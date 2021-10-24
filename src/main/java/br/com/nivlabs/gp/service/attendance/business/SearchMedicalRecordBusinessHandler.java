package br.com.nivlabs.gp.service.attendance.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.ApplicationMain;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.enums.ParameterAliasType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.DigitalDocument;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.PatientAllergy;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.AttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.MedicineInfoDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.patient.PatientService;
import br.com.nivlabs.gp.service.responsible.ResponsibleService;
import br.com.nivlabs.gp.service.userservice.UserService;
import br.com.nivlabs.gp.util.SecurityContextUtil;

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
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private UserService userService;

    /**
     * Busca Prontuário do atendimento por Identificador único do atendimento
     *
     * @param id Identificador único do atendimento
     * @return MedicalRecord Informações do prontuário de atendimento
     */
    @Transactional
    public MedicalRecordDTO byAttendanceId(Long id) {
        logger.info("Verificando se o atendimento informado existe. Atendimento :: {}", id);
        Attendance attendance = attendanceDao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Prontuário com código %s não encontrado", id)));

        Person person = attendance.getPatient().getPerson();

        checkParameters(attendance);

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
            medicalRecord.setLastAccommodation(medicalRecord.getEvents().get(medicalRecord.getEvents().size() - 1).getAccommodation());
            medicalRecord.setLascProfessional(medicalRecord.getEvents().get(medicalRecord.getEvents().size() - 1).getProfessional());
        }

        medicalRecord.getAllergies()
                .addAll(attendance.getPatient().getAllergies().stream().map(PatientAllergy::getDescription).collect(Collectors.toList()));

        medicalRecord.setAttendanceLevel(attendance.getLevel());
        return medicalRecord;
    }

    /**
     * Checa os parâmetros e aplica algumas regras de negócios na consulta de atendimento
     * 
     * @param attendance Atendimento
     * @param person Pessoa
     */
    @Transactional
    private void checkParameters(Attendance attendance) {
        if (ApplicationMain.SETTINGS.getBooleanValue(ParameterAliasType.BLOCKS_READING_THE_MEDICAL_RECORD_WITHOUT_ACTIVE_SERVICE)) {
            logger.info("O parâmetro que bloqueia a leitura de prontuário sem atendimento ativo está habilitado, iniciando processo de verificação...");
            attendanceDao.findByPatientAndExitDateTimeIsNull(new Patient(attendance.getPatient().getId()))
                    .orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, String.format(
                                                                                                        "Não há atendimento iniciado para o paciente de código %s e nome %s! Inicie um novo atendimento para acessar o prontuário do mesmo.",
                                                                                                        attendance.getPatient().getId(),
                                                                                                        attendance.getPatient().getPerson()
                                                                                                                .getFullName())));
        }
        if (!ApplicationMain.SETTINGS.getBooleanValue(ParameterAliasType.ENABLE_SERVICE_SHARING)) {
            logger.info("O parâmetro de compartilhamento de atendimentos está inativo, iniciando checagem do profissional...");
            logger.info("Iniciando busca de profissional pelo usuário da requisição...");
            UserInfoDTO userInfo = userService.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername());
            ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(userInfo.getDocument().getValue());
            if (responsibleInformations.getId() == null)
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Você não tem um profissional vinculado ao seu usuário.");
            logger.info("Profissional encontrado :: {} | {}", responsibleInformations.getId(), responsibleInformations.getFullName());
            if (attendance.getProfessional() != null && responsibleInformations.getId().equals(attendance.getProfessional().getId())) {
                logger.info("Verificando se o profissional é o responsável pelo atendimento ativo atual...");
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Você não é o profissional responsável pelo atendimento!");
            }

        }
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
        checkParameters(attendance);

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
            medicalRecord.setLastAccommodation(medicalRecord.getEvents().get(medicalRecord.getEvents().size() - 1).getAccommodation());
            medicalRecord.setLascProfessional(medicalRecord.getEvents().get(medicalRecord.getEvents().size() - 1).getProfessional());
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
     * Converte entidades relacionais de documentos para objeto de transferência
     * 
     * @param documents Lista de documentos (entidades)
     * @return Lista de documentos (DTO)
     */
    private List<DigitalDocumentDTO> convertDocuments(List<DigitalDocument> documents) {
        List<DigitalDocumentDTO> returnList = new ArrayList<>();
        documents.forEach(doc -> {
            DigitalDocumentDTO docToList = new DigitalDocumentDTO();
            docToList.setId(doc.getId());
            docToList.setCreatedAt(doc.getCreatedAt());
            docToList.setAttendanceEventId(doc.getAttendanceEvent().getId());
            docToList.setName(doc.getName());
            docToList.setType(doc.getType());
            returnList.add(docToList);
        });
        return returnList;
    }

    /**
     * Processa evento no prontuário
     * 
     * @param medicalRecord Objeto de transferência referente às informações do prontuário (Atendimento)
     * @param entity Objeto de entidade relacional referente ao evento do atendimento
     */
    @Transactional
    private void processEvent(MedicalRecordDTO medicalRecord, AttendanceEvent entity) {
        AttendanceEventDTO attendanceEventInfo = new AttendanceEventDTO();
        attendanceEventInfo.setId(entity.getId());
        attendanceEventInfo.setDatetime(entity.getEventDateTime());
        attendanceEventInfo.setDescription(entity.getTitle());
        attendanceEventInfo.setDocuments(convertDocuments(entity.getDocuments()));
        attendanceEventInfo.setAccommodation(convertAccomodation(entity.getAccommodation()));
        attendanceEventInfo.setProfessional(convertProfessional(entity.getResponsible()));

        medicalRecord.getEvents().add(attendanceEventInfo);
        if (entity.getEventType() == EventType.EVOLUTION) {
            processEvolution(medicalRecord, entity);
        } else if (entity.getEventType() == EventType.MEDICINE) {
            processMedications(medicalRecord, entity);
        }
    }

    /**
     * Converte entidade relacional de profissional responsável
     * 
     * @param responsible Profissional responsável (entidade)
     * @return Profissional responsável (DTO)
     */
    @Transactional
    private ResponsibleDTO convertProfessional(Responsible responsible) {
        if (responsible != null) {
            Person professionalPerson = responsible.getPerson();

            return new ResponsibleDTO(responsible.getId(), professionalPerson.getFullName(), professionalPerson.getSocialName(),
                    professionalPerson.getCpf(), professionalPerson.getBornDate(), professionalPerson.getPrincipalNumber(),
                    professionalPerson.getGender(), responsible.getProfessionalIdentity(), responsible.getInitialsIdentity());
        }
        return null;
    }

    /**
     * Converte entidade relacional de acomodação
     * 
     * @param accommodation Acomodação (entidade)
     * @return Acomodação (DTO)
     */
    @Transactional
    private AccommodationDTO convertAccomodation(Accommodation accommodation) {
        return new AccommodationDTO(accommodation.getId(), accommodation.getSector().getId(), accommodation.getDescription(),
                accommodation.getType());
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
}
