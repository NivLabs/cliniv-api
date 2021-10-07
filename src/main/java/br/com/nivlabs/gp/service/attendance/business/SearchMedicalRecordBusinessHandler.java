package br.com.nivlabs.gp.service.attendance.business;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.PatientAllergy;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.AttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.MedicineInfoDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.patient.PatientService;

/**
 * 
 * Camada de negócio para processos relacionados à buscas de informaões do prontuário
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class SearchMedicalRecordBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private AttendanceRepository attendanceDao;
    @Autowired
    private PatientService patientService;

    /**
     * Busca Prontuário do atendimento por Identificador único do atendimento
     *
     * @param id Identificador único do atendimento
     * @return MedicalRecord Informações do prontuário de atendimento
     */
    @Transactional
    public MedicalRecordDTO findByAttendanceId(Long id) {
        logger.info("Verificando se o atendimento informado existe. Atendimento :: {}", id);
        Attendance attendance = attendanceDao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Prontuário com código %s não encontrado", id)));
        Person person = attendance.getPatient().getPerson();
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO();

        medicalRecord.setId(attendance.getId());
        medicalRecord.setEntryDateTime(attendance.getEntryDateTime());
        medicalRecord.setReasonForEntry(attendance.getReasonForEntry());
        medicalRecord.setExitDateTime(attendance.getExitDateTime());
        medicalRecord.setPatientId(attendance.getPatient().getId());
        medicalRecord.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        medicalRecord.setFullName(person.getFullName());
        medicalRecord.setBloodType(person.getBloodType());
        medicalRecord.setSocialName(person.getSocialName());
        medicalRecord.setPrincipalNumber(person.getPrincipalNumber());
        medicalRecord.setCnsNumber(attendance.getPatient().getCnsNumber());
        medicalRecord.setBornDate(person.getBornDate());
        medicalRecord.setGender(person.getGender());

        processEvents(attendance, medicalRecord);

        if (!medicalRecord.getEvents().isEmpty()) {
            medicalRecord.setLastAccommodation(getLastAccommodationByPatientId(medicalRecord.getEvents()));
        }

        medicalRecord.getAllergies()
                .addAll(attendance.getPatient().getAllergies().stream().map(PatientAllergy::getDescription).collect(Collectors.toList()));

        medicalRecord.setAttendanceLevel(attendance.getLevel());
        return medicalRecord;
    }

    /**
     * Realiza a busca de um prontuário ativo por código de paciente
     * 
     * @param patientId Identificador único do paciente
     * @return Prontuário de atendimento ativo do paciente
     */
    @Transactional
    public MedicalRecordDTO getActiveMedicalRecord(Long patientId) {
        PatientInfoDTO patient = patientService.findByPatientId(patientId);
        Attendance attendance = attendanceDao.findByPatientAndExitDateTimeIsNull(new Patient(patient.getId()))
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format(
                                                                                         "Nenhum atendimento ativo encontrado para %s, inicie um novo atendimento para o paciente.",
                                                                                         patient.getFullName())));
        MedicalRecordDTO medicalRecord = new MedicalRecordDTO();
        medicalRecord.setId(attendance.getId());
        medicalRecord.setEntryDateTime(attendance.getEntryDateTime());
        medicalRecord.setReasonForEntry(attendance.getReasonForEntry());
        medicalRecord.setExitDateTime(attendance.getExitDateTime());
        medicalRecord.setPatientId(attendance.getPatient().getId());
        medicalRecord.setDocument(patient.getDocument());
        medicalRecord.setFullName(patient.getFullName());
        medicalRecord.setBloodType(patient.getBloodType());
        medicalRecord.setSocialName(patient.getSocialName());
        medicalRecord.setPrincipalNumber(patient.getPrincipalNumber());
        medicalRecord.setCnsNumber(attendance.getPatient().getCnsNumber());
        medicalRecord.setBornDate(patient.getBornDate());
        medicalRecord.setGender(patient.getGender());

        processEvents(attendance, medicalRecord);

        if (!medicalRecord.getEvents().isEmpty()) {
            medicalRecord.setLastAccommodation(getLastAccommodationByPatientId(medicalRecord.getEvents()));
        }

        medicalRecord.setAttendanceLevel(attendance.getLevel());

        return medicalRecord;
    }

    /**
     * Processa eventos do atendimento no prontuário
     * 
     * @param entity Objeto de entidade relacional referente ao atendimento
     * @param medicalRecord Objeto de transferência referente às informações do prontuário (Atendimento)
     */
    @Transactional
    private void processEvents(Attendance entity, MedicalRecordDTO medicalRecord) {
        entity.getEvents().forEach(event -> {
            processEvent(medicalRecord, event);
        });
    }

    /**
     * Processa evento no prontuário
     * 
     * @param medicalRecord Objeto de transferência referente às informações do prontuário (Atendimento)
     * @param entity Objeto de entidade relacional referente ao evento do atendimento
     */
    @Transactional
    private void processEvent(MedicalRecordDTO medicalRecord, AttendanceEvent entity) {
        medicalRecord.getEvents().add(entity.getDTO());
        if (entity.getEventType() == EventType.EVOLUTION) {
            processEvolution(medicalRecord, entity);
        } else if (entity.getEventType() == EventType.MEDICINE) {
            processMedications(medicalRecord, entity);
        }
    }

    /**
     * Processa evoluções no prontuário
     * 
     * @param medicalRecord Objeto de transferência referente às informações do prontuário (Atendimento)
     * @param entity Objeto de entidade relacional referente ao evento do atendimento
     */
    @Transactional
    private void processEvolution(MedicalRecordDTO medicalRecord, AttendanceEvent entity) {
        EvolutionInfoDTO evolution = new EvolutionInfoDTO();
        evolution.setId(entity.getId());
        evolution.setAccommodationId(medicalRecord.getLastAccommodation() != null ? medicalRecord.getLastAccommodation().getId() : null);
        evolution.setAttendanceId(medicalRecord.getId());
        evolution.setDescription("EVOLUÇÃO");
        evolution.setDatetime(entity.getEventDateTime());
        evolution.setResponsibleName(entity.getResponsible().getPerson().getFullName());

        medicalRecord.getEvolutions().add(evolution);
    }

    /**
     * Processa medicamentos no protuário
     * 
     * @param medicalRecord Objeto de transferência referente às informações do prontuário (Atendimento)
     * @param entity Objeto de entidade relacional referente ao evento do atendimento
     */
    @Transactional
    private void processMedications(MedicalRecordDTO medicalRecord, AttendanceEvent entity) {
        MedicineInfoDTO medicine = new MedicineInfoDTO();
        medicine.setId(entity.getId());
        medicine.setDatetime(entity.getEventDateTime());
        medicine.setDescription(entity.getTitle());
        medicine.setAmount(entity.getObservations());
        medicine.setResponsibleForTheAdministration(entity.getResponsible().getPerson().getFullName());

        medicalRecord.getMedicines().add(medicine);
    }

    /**
     * Pega a última acomodação do paciente baseada nos eventos do prontuário
     *
     * @param listOfEntities Lista de acomotações do existentes no atendimento
     * @return Última acomodação
     */
    @Transactional
    private AccommodationDTO getLastAccommodationByPatientId(List<AttendanceEventDTO> listOfEntities) {
        return listOfEntities.get(listOfEntities.size() - 1).getAccommodation();
    }

}
