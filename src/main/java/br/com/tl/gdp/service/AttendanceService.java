package br.com.tl.gdp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tl.gdp.controller.filters.AttendanceFilters;
import br.com.tl.gdp.exception.ObjectNotFoundException;
import br.com.tl.gdp.exception.ValidationException;
import br.com.tl.gdp.models.domain.Attendance;
import br.com.tl.gdp.models.domain.AttendanceEvent;
import br.com.tl.gdp.models.domain.EventType;
import br.com.tl.gdp.models.domain.Patient;
import br.com.tl.gdp.models.domain.Person;
import br.com.tl.gdp.models.domain.Responsible;
import br.com.tl.gdp.models.domain.Sector;
import br.com.tl.gdp.models.dto.AttendanceDTO;
import br.com.tl.gdp.models.dto.AttendanceEventDTO;
import br.com.tl.gdp.models.dto.DocumentDTO;
import br.com.tl.gdp.models.dto.MedicalRecordDTO;
import br.com.tl.gdp.models.dto.NewAttandenceDTO;
import br.com.tl.gdp.models.dto.PatientInfoDTO;
import br.com.tl.gdp.models.dto.SectorDTO;
import br.com.tl.gdp.models.enums.DocumentType;
import br.com.tl.gdp.models.enums.EntryType;
import br.com.tl.gdp.repository.AttendanceEventRepository;
import br.com.tl.gdp.repository.AttendanceRepository;

/**
 * 
 * Classe AttendanceService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Service
public class AttendanceService implements GenericService<Attendance, Long> {

    @Autowired
    private AttendanceRepository dao;
    @Autowired
    private AttendanceEventRepository attendanceEventRepo;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SectorService sectorService;

    public Page<AttendanceDTO> getAttendancesPage(AttendanceFilters filters, Pageable pageRequest) {
        return dao.resumedList(filters, pageRequest);
    }

    /**
     * Busca histórico de Atendimentos por Paciente
     * 
     * @param patientId
     * @return lista de AttendanceDTO
     */
    public List<AttendanceDTO> getAttandenceByPatientId(Long patientId) {
        List<Attendance> list = dao.findByPatient(new Patient(patientId));

        if (list.isEmpty())
            throw new ObjectNotFoundException(String.format("Não existe atendimento para o paciente %s", patientId));

        List<AttendanceDTO> listOfDto = new ArrayList<>();
        list.forEach(attendance -> {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setEntryDatetime(attendance.getDateTimeEntry());
            dto.setEntryCause(attendance.getReasonForEntry());
            dto.setIsFinished(Boolean.valueOf(attendance.getDateTimeExit() != null));
            listOfDto.add(dto);
        });
        return listOfDto;
    }

    /**
     * Busca Prontuário do atendimento por Id
     * 
     * @param id
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO findMedicalRecordByAttendanceId(Long id) {
        Attendance objectFromDb = dao.findById(id).orElseThrow(
                                                               () -> new ObjectNotFoundException(
                                                                       String.format("Prontuário com código %s não encontrad", id)));
        Person person = objectFromDb.getPatient().getPerson();
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO();
        BeanUtils.copyProperties(person, medicalRecord);
        medicalRecord.setId(objectFromDb.getId());
        medicalRecord.setPatientId(objectFromDb.getPatient().getId());
        medicalRecord.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

        List<AttendanceEvent> listOfEventsFromDb = attendanceEventRepo.findByAttendanceId(objectFromDb.getId());
        listOfEventsFromDb.forEach(event -> medicalRecord.getEvents().add(new AttendanceEventDTO(event.getId(),
                event.getEventDateTime(), event.getEventType().getDescription(), event.getDocumentId())));
        return medicalRecord;
    }

    @Override
    public Attendance update(Long id, Attendance entity) {
        Attendance auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(Attendance entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Attendance auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    @Override
    public Attendance persist(Attendance entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    public void closeAttendance(Long id) {
        Attendance auxEntity = findById(id);
        auxEntity.setDateTimeExit(LocalDateTime.now());
        update(id, auxEntity);
    }

    /**
     * Realiza a criação de uma visita de paciente à partir do DTO
     * 
     * @param NewAttandenceDTO
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO persistNewAttendance(NewAttandenceDTO visitDto) {
        MedicalRecordDTO attendance = null;
        SectorDTO sectorToPatient = sectorService.findById(visitDto.getSectorId());
        try {
            attendance = getActiveMedicalRecord(visitDto.getPatientId());
        } catch (ValidationException e) {

            PatientInfoDTO savedPatient = patientService.findByPateintId(visitDto.getPatientId());

            Attendance convertedAttendance = new Attendance();
            convertedAttendance.setReasonForEntry(visitDto.getEntryCause());
            convertedAttendance.setPatient(new Patient(savedPatient.getId()));
            convertedAttendance.setEntryType(visitDto.getEventTypeId().intValue() == 2 ? EntryType.EMERGENCY : EntryType.CLINICAL);
            convertedAttendance.setSector(new Sector(sectorToPatient.getId()));
            convertedAttendance = persist(convertedAttendance);
            createEntryEvent(convertedAttendance, visitDto);
            attendance = getActiveMedicalRecord(visitDto.getPatientId());

            return attendance;
        }
        if (attendance != null) {
            throw new ValidationException(String.format(
                                                        "O paciente de código %s e nome %s já possui um atendimento ativo, favor realizar a alta do mesmo para iniciar um novo.",
                                                        attendance.getPatientId(), attendance.getFirstName()));
        }
        return attendance;
    }

    /**
     * @param convertedAtendance
     */
    private void createEntryEvent(Attendance convertedAtendance, NewAttandenceDTO request) {
        AttendanceEvent entryEvent = new AttendanceEvent();
        entryEvent.setPatient(new Patient(convertedAtendance.getPatient().getId()));
        entryEvent.setEventDateTime(convertedAtendance.getDateTimeEntry());
        entryEvent.setTitle(convertedAtendance.getReasonForEntry());
        entryEvent.setAttendance(new Attendance(convertedAtendance.getId()));

        if (request.getResponsibleId() != null)
            // Verificar Responsável
            entryEvent.setResponsible(new Responsible(request.getResponsibleId()));

        if (request.getEventTypeId() != 1L && request.getEventTypeId() != 2L && request.getEventTypeId() != 3L)
            throw new ValidationException(
                    "O tipo de evento informado é inválido. Tipos de eventos esperados: Entrada de paciente (1 - Entrada, 2 - Entrada Emergência ou 3 - Entrada Ambulatório)");
        // Verificar Tipo do evento
        entryEvent.setEventType(new EventType(request.getEventTypeId()));

        attendanceEventRepo.save(entryEvent);
    }

    /**
     * Busca uma lista de Atendimentos baseados no documento do paciente
     * 
     * @param documentType
     * @param documentValue
     * @return
     */
    public List<AttendanceDTO> findByDocument(DocumentType documentType, String documentValue) {
        return null;
    }

    /**
     * Realiza a busca de um prontuário ativo por código de paciente
     * 
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO getActiveMedicalRecord(Long patientId) {
        PatientInfoDTO patient = patientService.findByPateintId(patientId);
        Attendance attendanceFromDb = dao.findByPatientAndDateTimeExitIsNull(new Patient(patient.getId()))
                .orElseThrow(() -> new ValidationException(
                        String.format("Nenhum atendimento ativo encontrado para %s, inicie um novo atendimento para o paciente.",
                                      patient.getFirstName())));
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO();
        BeanUtils.copyProperties(patient, medicalRecord);
        medicalRecord.setId(attendanceFromDb.getId());
        medicalRecord.setPatientId(attendanceFromDb.getPatient().getId());
        medicalRecord.setDocument(new DocumentDTO(patient.getDocument().getType(), patient.getDocument().getValue()));

        List<AttendanceEvent> listOfEventsFromDb = attendanceEventRepo.findByAttendanceId(attendanceFromDb.getId());
        listOfEventsFromDb.forEach(event -> medicalRecord.getEvents().add(new AttendanceEventDTO(event.getId(),
                event.getEventDateTime(), event.getEventType().getDescription(), event.getDocumentId())));
        return medicalRecord;
    }

}
