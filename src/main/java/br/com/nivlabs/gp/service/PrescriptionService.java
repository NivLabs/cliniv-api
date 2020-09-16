package br.com.nivlabs.gp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.EventTypeDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.PrescriptionInfoDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;

/**
 * Camada de serviço para manipulação de prescrição médica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class PrescriptionService implements GenericService {

    private static final String TODAY = "TODAY";
    private static final String HOSPITAL_LOGO = "HOSPITAL_LOGO";
    private static final String REQUESTER_NAME = "READER_NAME";
    private static final String VISIT_ID = "VISIT_ID";
    private static final String REPORT_SOURCE = "reports/Prescricao.jrxml";

    @Autowired
    private Logger logger;

    @Autowired
    private InstituteService instituteServive;

    @Autowired
    private AttendanceEventService eventService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserService userSerive;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ResponsibleService responsibleService;

    /**
     * Cria uma nova prescrição médica do paciente
     * 
     * @param request
     * @return
     */
    public PrescriptionInfoDTO createPrescription(PrescriptionInfoDTO request, String username) {
        logger.info("Iniciando a criação de uma nova prescrição médica para o atendimento {}", request.getAttendanceId());
        MedicalRecordDTO medicalRecord = attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());

        logger.info("Verificando o usuário da solicitação");
        UserInfoDTO user = userSerive.findByUserName(username);

        logger.info("Iniciando criação do documento digital da prescrição");
        try {
            DigitalDocumentDTO document = reportService.createDocumentFromReport(request.getAttendanceId(), "Prescrição Médica",
                                                                                 getReportParam(request, user),
                                                                                 new ClassPathResource(REPORT_SOURCE).getInputStream());
            createDocumentEvent(request, document, user, medicalRecord);
        } catch (IOException e) {
            logger.error("Falha ao gerar documento de evolução", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento de evolução");
        }

        return null;
    }

    private ReportParam getReportParam(PrescriptionInfoDTO request, UserInfoDTO user) {
        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        InstituteDTO instituteDTO = instituteServive.getSettings();
        String logoBase64 = instituteDTO.getCustomerInfo().getLogoBase64();

        logger.info("Separando parâmetros e valores do relatório...");
        ReportParam params = new ReportParam();
        params.getParams().put(VISIT_ID, request.getAttendanceId());
        params.getParams().put("DOC_TITLE", "PRESCRIÇÃO MÉDICA");
        params.getParams().put(REQUESTER_NAME, user.getFullName());
        params.getParams().put(HOSPITAL_LOGO, logoBase64);
        params.getParams().put(TODAY, new Date());

        logger.info("Parâmetros configurados e prontos para a criação do documento");

        return params;
    }

    /**
     * Cria um evento de documento para a prescrição
     * 
     * @param request
     * @param document
     * @param requestOwner
     */
    private void createDocumentEvent(PrescriptionInfoDTO request, DigitalDocumentDTO document, UserInfoDTO requestOwner,
                                     MedicalRecordDTO medicalRecord) {
        logger.info("Iniciando criação de Evento de atendimento para prescrição...");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setEventType(new EventTypeDTO(6L, "PRESCRICAO", "Geração de Prescrição"));
        event.setAttendanceId(request.getAttendanceId());
        event.setDocuments(Arrays.asList(document));
        event.setEventDateTime(LocalDateTime.now());
        event.setObservations("Criação da prescrição");
        event.setResponsible(getResponsibleFromUser(requestOwner));
        event.setAccommodation(medicalRecord.getLastAccommodation());
        logger.info("Evento processado, inserindo evento na base de dados...");

        eventService.persistNewAttendanceEvent(event);
        logger.info("Evento inserido com sucesso!");
    }

    /**
     * Busca o responsável pela requisição da prescrição baseado no usuário
     * 
     * @param requestOwner
     * @return
     */
    private ResponsibleDTO getResponsibleFromUser(UserInfoDTO requestOwner) {
        logger.info("Iniciando busca de responsável pelo usuário da requisição...");
        ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(requestOwner.getDocument().getValue());
        if (responsibleInformations.getId() == null)
            throw new HttpException(HttpStatus.FORBIDDEN, "Sem presmissão! Você não tem um profissional vinculado ao seu usuário.");
        logger.info("Profissional encontrado :: {}", responsibleInformations.getFullName());

        logger.info("Realizando processamento do profissional para a requisição de prescrição");
        ResponsibleDTO responsible = new ResponsibleDTO();
        BeanUtils.copyProperties(responsibleInformations, responsible);
        return responsible;
    }
}
