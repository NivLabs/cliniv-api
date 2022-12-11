package br.com.nivlabs.cliniv.service.attendance.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.enums.EntryType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Accommodation;
import br.com.nivlabs.cliniv.models.domain.Attendance;
import br.com.nivlabs.cliniv.models.domain.AttendanceEvent;
import br.com.nivlabs.cliniv.models.domain.DigitalDocument;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.models.dto.MedicalRecordDTO;
import br.com.nivlabs.cliniv.models.dto.NewAttandenceDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.models.dto.UserInfoDTO;
import br.com.nivlabs.cliniv.repository.AttendanceEventRepository;
import br.com.nivlabs.cliniv.repository.AttendanceRepository;
import br.com.nivlabs.cliniv.repository.DigitalDocumentRepository;
import br.com.nivlabs.cliniv.repository.PatientRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.report.ReportService;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

/**
 * 
 * Camada de serviço para criação de um novo atendimento na aplicação
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class CreateNewAttendanceBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private AttendanceRepository attendanceRepo;
    @Autowired
    private AttendanceEventRepository attendanceEventRepo;
    @Autowired
    private PatientRepository patientDao;
    @Autowired
    DigitalDocumentRepository digitalDocumentRepo;
    @Autowired
    SearchAttendanceBusinessHandler searchAttendanceBusinessHandler;
    @Autowired
    SearchMedicalRecordBusinessHandler searchMedicalRecordBusinessHandler;
    @Autowired
    ReportService reportService;
    @Autowired
    ResponsibleService responsibleService;
    @Autowired
    UserService userService;

    /**
     * Realiza a criação de um novo atendimento à partir do DTO
     *
     * @param NewAttandenceDTO Novo atendimento
     * @return MedicalRecordDTO Registro de atendimento (Prontuário do atendimento)
     */
    public MedicalRecordDTO create(NewAttandenceDTO request) {
        logger.info("Iniciando processo de criação de um novo atendimento para o paciente :: ID: {}", request.getPatientId());
        MedicalRecordDTO attendance = null;
        try {
            logger.info("Checando se há algum atendimento ativo para o paciente em questão...");
            searchMedicalRecordBusinessHandler.getActiveMedicalRecord(request.getPatientId());
        } catch (HttpException e) {
            logger.info("Nenhum atendimento ativo encontrado, iniciando criação de um novo atendimento.");
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                if (!patientDao.existsById(request.getPatientId())) {
                    throw new HttpException(HttpStatus.NOT_FOUND,
                            String.format("Paciente com o identificador %s não encontrado!", request.getPatientId()));
                }
                Attendance convertedAttendance = new Attendance();
                convertedAttendance.setId(null);
                convertedAttendance.setReasonForEntry(request.getEntryCause());
                convertedAttendance.setLevel(request.getLevel());
                convertedAttendance.setPatient(new Patient(request.getPatientId()));
                convertedAttendance.setCurrentAccommodation(new Accommodation(request.getAccommodationId(), null, null, null));
                if (request.getResponsibleId() != null && responsibleService.findById(request.getPatientId()) != null) {
                    convertedAttendance.setProfessional(new Responsible(request.getResponsibleId()));
                } else {
                    logger.info("Verificando o usuário da solicitação");
                    UserInfoDTO user = userService.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername());
                    ResponsibleInfoDTO responsible = getResponsibleFromUser(user);
                    convertedAttendance.setProfessional(new Responsible(responsible.getId()));
                }

                switch (request.getEventType()) {
                    case MEDICAL_EMERGENCY:
                        convertedAttendance.setEntryType(EntryType.EMERGENCY);
                        break;
                    case MEDICAL_APPOINTMENT:
                        convertedAttendance.setEntryType(EntryType.CLINICAL);
                        break;
                    case REMOTE_MEDICAL_APPOINTMENT:
                        convertedAttendance.setEntryType(EntryType.REMOTE);
                        break;
                    default:
                        convertedAttendance.setEntryType(EntryType.GENERIC);
                        break;
                }

                convertedAttendance = attendanceRepo.save(convertedAttendance);

                createEntryEvent(convertedAttendance, request);
                attendance = searchMedicalRecordBusinessHandler.getActiveMedicalRecord(request.getPatientId());

                return attendance;
            }
        }

        throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, String
                .format("O paciente de código %s já possui um atendimento ativo, favor realizar a alta do mesmo para iniciar um novo.",
                        request.getPatientId()));

    }

    /**
     * Cria evento de entrada
     *
     * @param attendanceEntity Atendimento
     * @param newAttendanceRequest Requisição de novo atendimento
     */
    private void createEntryEvent(Attendance attendanceEntity, NewAttandenceDTO newAttendanceRequest) {
        AttendanceEvent entryEvent = new AttendanceEvent();
        entryEvent.setEventDateTime(attendanceEntity.getEntryDateTime());
        entryEvent.setTitle("INÍCIO DO ATENDIMENTO");
        entryEvent.setAttendance(new Attendance(attendanceEntity.getId()));
        entryEvent.setAccommodation(attendanceEntity.getCurrentAccommodation());
        entryEvent.setObservations(newAttendanceRequest.getEntryCause());

        if (newAttendanceRequest.getResponsibleId() != null)
            // Verificar Responsável
            entryEvent.setResponsible(new Responsible(newAttendanceRequest.getResponsibleId()));

        entryEvent.setEventType(newAttendanceRequest.getEventType());

        entryEvent = attendanceEventRepo.save(entryEvent);

        if (entryEvent.getObservations() != null) {
            DigitalDocumentDTO digitalDocumentoFromDocumentTemplate = reportService
                    .generateDocumentFromFormatedText(entryEvent.getId(), entryEvent.getTitle(), entryEvent.getObservations());
            logger.info("Documento sendo processado :: Código do evento -> {} | Nome -> {}", entryEvent.getId(), entryEvent.getTitle());
            DigitalDocument document = new DigitalDocument();
            document.setBase64(digitalDocumentoFromDocumentTemplate.getBase64());
            document.setName(digitalDocumentoFromDocumentTemplate.getName());
            document.setType(digitalDocumentoFromDocumentTemplate.getType());
            document.setCreatedAt(digitalDocumentoFromDocumentTemplate.getCreatedAt());
            document.setAttendanceEvent(new AttendanceEvent(entryEvent.getId()));

            document = digitalDocumentRepo.save(document);
            logger.info("Documentos criados com sucesso!");
        }
    }

    /**
     * Busca o responsável pela requisição da prescrição baseado no usuário
     * 
     * @param requestOwner
     * @return
     */
    private ResponsibleInfoDTO getResponsibleFromUser(UserInfoDTO requestOwner) {
        try {
            logger.info("Iniciando busca de responsável pelo usuário da requisição...");
            var responsible = responsibleService.findByCpf(requestOwner.getDocument().getValue());
            if (responsible.getId() == null) {
                return null;
            }
            return responsible;
        } catch (HttpException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }
}
