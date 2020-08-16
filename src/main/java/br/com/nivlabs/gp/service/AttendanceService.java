package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.enums.EntryType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.DigitalDocument;
import br.com.nivlabs.gp.models.domain.EventType;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.PatientAllergy;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.dto.AccomodationDTO;
import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.models.dto.AttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.CloseAttandenceDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.EventTypeDTO;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttandenceDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceEventRepository;
import br.com.nivlabs.gp.repository.AttendanceRepository;

/**
 * Classe AttendanceService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 * @since 8 de set de 2019
 */
@Service
public class AttendanceService implements GenericService {

    private static final String CLOSE_ATTENDANCE_TEXT = "Encerramento de atendimento";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AttendanceRepository dao;
    @Autowired
    private AttendanceEventRepository attendanceEventRepo;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendanceEventService attendanceEventService;

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
            throw new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Não existe atendimento para o paciente %s", patientId));

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
        logger.info("Verificando se o atendimento informado existe. Atendimento :: {}", id);
        Attendance objectFromDb = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Prontuário com código %s não encontrado", id)));
        Person person = objectFromDb.getPatient().getPerson();
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO();
        BeanUtils.copyProperties(person, medicalRecord);
        medicalRecord.setId(objectFromDb.getId());
        medicalRecord.setPatientId(objectFromDb.getPatient().getId());
        medicalRecord.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));
        medicalRecord.setSusNumber(objectFromDb.getPatient().getSusNumber());

        objectFromDb.getEvents().forEach(event -> medicalRecord.getEvents().add(new AttendanceEventDTO(event.getId(),
                event.getEventDateTime(), event.getEventType().getDescription(), convertDocuments(event.getDocuments()),
                event.getAccomodation().getDTO())));
        objectFromDb.getEvolutions().forEach(evolution -> medicalRecord.getEvolutions()
                .add(new EvolutionInfoDTO(evolution.getId(), id, null, evolution.getDescription(), evolution.getDatetime())));

        if (!medicalRecord.getEvents().isEmpty())
            medicalRecord.setLastAccommodation(getLastAccomodationByPatientId(medicalRecord.getEvents()));
        medicalRecord.getAllergies()
                .addAll(objectFromDb.getPatient().getAllergies().stream().map(PatientAllergy::getDescription).collect(Collectors.toList()));
        return medicalRecord;
    }

    private List<DigitalDocumentDTO> convertDocuments(List<DigitalDocument> documents) {
        List<DigitalDocumentDTO> returnList = new ArrayList<>();
        documents.forEach(doc -> {
            DigitalDocumentDTO docToList = new DigitalDocumentDTO();
            BeanUtils.copyProperties(doc, docToList, "base64");
            returnList.add(docToList);
        });
        return returnList;
    }

    public Attendance persist(Attendance entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    /**
     * Realiza a criação de uma visita de paciente à partir do DTO
     *
     * @param NewAttandenceDTO Novo atendimento
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO persistNewAttendance(NewAttandenceDTO visitDto) {
        MedicalRecordDTO attendance = null;
        try {
            getActiveMedicalRecord(visitDto.getPatientId());
        } catch (HttpException e) {
            if (e.getStatus().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
                PatientInfoDTO savedPatient = patientService.findByPatientId(visitDto.getPatientId());

                Attendance convertedAttendance = new Attendance();
                convertedAttendance.setReasonForEntry(visitDto.getEntryCause());
                convertedAttendance.setPatient(new Patient(savedPatient.getId()));
                convertedAttendance.setEntryType(
                                                 visitDto.getEventTypeId().intValue() == 2 ? EntryType.EMERGENCY : EntryType.CLINICAL);
                convertedAttendance = persist(convertedAttendance);
                createEntryEvent(convertedAttendance, visitDto);
                attendance = getActiveMedicalRecord(visitDto.getPatientId());

                return attendance;
            }
        }

        throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, String.format(
                                                                               "O paciente de código %s já possui um atendimento ativo, favor realizar a alta do mesmo para iniciar um novo.",
                                                                               visitDto.getPatientId()));

    }

    /**
     * Cria evento de entrada
     *
     * @param convertedAtendance
     * @param request
     */
    private void createEntryEvent(Attendance convertedAtendance, NewAttandenceDTO request) {
        AttendanceEvent entryEvent = new AttendanceEvent();
        entryEvent.setEventDateTime(convertedAtendance.getDateTimeEntry());
        entryEvent.setTitle(convertedAtendance.getReasonForEntry());
        entryEvent.setAttendance(new Attendance(convertedAtendance.getId()));

        if (request.getResponsibleId() != null)
            // Verificar Responsável
            entryEvent.setResponsible(new Responsible(request.getResponsibleId()));

        if (request.getEventTypeId() != 1L && request.getEventTypeId() != 2L && request.getEventTypeId() != 3L)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O tipo de evento informado é inválido. Tipos de eventos esperados: Entrada de paciente (1 - Entrada, 2 - Entrada Emergência ou 3 - Entrada Ambulatório)");
        // Verificar Tipo do evento
        entryEvent.setEventType(new EventType(request.getEventTypeId()));

        attendanceEventRepo.save(entryEvent);
    }

    /**
     * Realiza a busca de um prontuário ativo por código de paciente
     *
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO getActiveMedicalRecord(Long patientId) {
        PatientInfoDTO patient = patientService.findByPatientId(patientId);
        Attendance attendanceFromDb = dao.findByPatientAndDateTimeExitIsNull(new Patient(patient.getId()))
                .orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, String.format(
                                                                                                    "Nenhum atendimento ativo encontrado para %s, inicie um novo atendimento para o paciente.",
                                                                                                    patient.getFullName())));
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO();
        BeanUtils.copyProperties(patient, medicalRecord);
        medicalRecord.setId(attendanceFromDb.getId());
        medicalRecord.setPatientId(attendanceFromDb.getPatient().getId());
        medicalRecord.setDocument(new DocumentDTO(patient.getDocument().getType(), patient.getDocument().getValue()));

        attendanceFromDb.getEvents().forEach(event -> medicalRecord.getEvents().add(new AttendanceEventDTO(event.getId(),
                event.getEventDateTime(), event.getEventType().getDescription(),
                convertDocuments(event.getDocuments()), event.getAccomodation().getDTO())));

        attendanceFromDb.getEvolutions().forEach(evolution -> medicalRecord.getEvolutions()
                .add(new EvolutionInfoDTO(evolution.getId(), evolution.getAttendance().getId(), null, evolution.getDescription(),
                        evolution.getDatetime())));

        if (!medicalRecord.getEvents().isEmpty())
            medicalRecord.setLastAccommodation(getLastAccomodationByPatientId(medicalRecord.getEvents()));
        return medicalRecord;
    }

    /**
     * Pega a última acomodação do paciente baseada nos eventos do prontuário
     *
     * @param listOfEventsFromDb
     * @return
     */
    private AccomodationDTO getLastAccomodationByPatientId(List<AttendanceEventDTO> listOfEventsFromDb) {
        return listOfEventsFromDb.get(listOfEventsFromDb.size() - 1).getAccomodation();
    }

    /**
     * Realiza alta de paciente com atendimento ativo
     *
     * @param id
     * @param request
     */
    public void closeAttendance(Long id, CloseAttandenceDTO request) {
        logger.info("Iniciando processo de encerramento de atendimento,\nVerificando situação do atendimento solicitado...");
        Attendance attendanceFromDb = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                "Não é possível encerrar um atendimento inexistente"));
        if (attendanceFromDb.getDateTimeExit() != null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Este atendimento já foi encerrado");
        }
        NewAttendanceEventDTO newAttendanceEvent = createAttendanceRequest(attendanceFromDb, request);
        attendanceFromDb.setDateTimeExit(newAttendanceEvent.getEventDateTime());
        dao.save(attendanceFromDb);
    }

    /**
     * Cria um evento de encerramento de atendimento (Alta)
     *
     * @param attendance
     * @param request
     * @return
     */
    private NewAttendanceEventDTO createAttendanceRequest(Attendance attendance, CloseAttandenceDTO request) {
        logger.info("Criando evento de atendimento para alta de paciente");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setAttendanceId(attendance.getId());
        event.setEventDateTime(request.getDatetime());
        event.setEventType(new EventTypeDTO(request.getEventTypeId(), CLOSE_ATTENDANCE_TEXT, CLOSE_ATTENDANCE_TEXT));
        event.setAccomodation(new AccomodationDTO(attendance.getCurrentSector().getId()));
        event.setResponsible(getResponsibleFromUserSession());
        event.setObservations(request.getObservations());

        attendanceEventService.persistNewAttendanceEvent(event);
        logger.info("Evento de Atendimento para alta de paciente criado com sucesso!");

        return event;
    }

    /**
     * Busca o usuário logado na sessão
     *
     * @return
     */
    private ResponsibleDTO getResponsibleFromUserSession() {
        logger.info("Buscando usuário da sessão...");
        String userName = ((UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserInfoDTO logedUser = userService.findByUserName(userName);

        logger.info("Iniciando busca de responsável pelo usuário da requisição...");
        ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(logedUser.getDocument().getValue());
        if (responsibleInformations.getId() == null)
            throw new HttpException(HttpStatus.FORBIDDEN, "Sem presmissão! Você não tem um profissional vinculado ao seu usuário.");
        logger.info("Profissional encontrado :: {}", responsibleInformations.getFullName());

        logger.info("Realizando processamento do profissional para a requisição de evolução clínica");
        ResponsibleDTO responsible = new ResponsibleDTO();
        BeanUtils.copyProperties(responsibleInformations, responsible);
        return responsible;
    }

}
