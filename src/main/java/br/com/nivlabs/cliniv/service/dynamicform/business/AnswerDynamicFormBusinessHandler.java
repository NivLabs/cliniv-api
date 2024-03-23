package br.com.nivlabs.cliniv.service.dynamicform.business;

import br.com.nivlabs.cliniv.enums.EventType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.DynamicFormAnswered;
import br.com.nivlabs.cliniv.models.dto.*;
import br.com.nivlabs.cliniv.report.ReportParam;
import br.com.nivlabs.cliniv.repository.DynamicFormAnsweredRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.InstituteService;
import br.com.nivlabs.cliniv.service.attendance.AttendanceService;
import br.com.nivlabs.cliniv.service.report.ReportService;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import br.com.nivlabs.cliniv.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Componente específico para respostas de formulários dinâmicos
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 */
@Component
public class AnswerDynamicFormBusinessHandler implements BaseBusinessHandler {

    private static final String TODAY = "TODAY";
    private static final String HOSPITAL_LOGO = "HOSPITAL_LOGO";
    private static final String REQUESTER_NAME = "READER_NAME";
    private static final String VISIT_ID = "VISIT_ID";
    private static final String USER_ID = "ID_USUARIO";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final String REPORT_PATH = "reports/dynamicForm.jrxml";

    @Autowired
    private Logger logger;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private InstituteService instituteServive;

    @Autowired
    private UserService userSerive;

    @Autowired
    private ResponsibleService responsibleService;

    @Autowired
    private DynamicFormAnsweredRepository formRepo;

    /**
     * Cria um novo questionário respondido de anamnese
     *
     * @param request
     * @return
     */
    @Transactional
    public NewDynamicFormAnsweredDTO answer(Long attendanceId, NewDynamicFormAnsweredDTO request) {
        logger.info("Iniciando o preenchimento de um novo formulário dinâmico...");
        MedicalRecordDTO medicalRecord = attendanceService.findMedicalRecordByAttendanceId(attendanceId);
        logger.info("Verificando o usuário da solicitação");
        UserInfoDTO user = userSerive.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername());

        logger.info("Validando respostas...");
        request.getListOfResponse().forEach(this::validateQuestions);

        try {
            createAnsweredForm(user.getId(), attendanceId, request);
            logger.info("Preparando formulário dinâmico...");

            DigitalDocumentDTO document = reportService
                    .genareteDocumentFromJxmlStream(attendanceId, request.getDocumentTitle(),
                            getAnamnesisReportParams(attendanceId, request, user),
                            new ClassPathResource(REPORT_PATH).getInputStream());
            createAnamneseDocumentEvent(attendanceId, request, medicalRecord, document, user);
        } catch (IOException e) {
            logger.error("Falha ao gerar documento de Anamnese", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento de anamnese");
        }

        return request;
    }

    /**
     * Insere os itens para o formulário dinâmico da aplicação
     *
     * @param responsibleId
     * @param attendanceId
     * @param request
     */
    @Transactional
    void createAnsweredForm(Long responsibleId, Long attendanceId, NewDynamicFormAnsweredDTO request) {
        logger.info("Iniciando processo de inserção de respostas do formulário dinâmico :: {}", request.getListOfResponse());
        logger.info("Removendo respostas anteriores...");
        formRepo.deleteByResponsibleId(responsibleId);
        logger.info("Base limpa. Iniciando processo de inserção de novas respostas...");
        request.getListOfResponse().forEach(response -> {
            DynamicFormAnswered responseConverted = new DynamicFormAnswered();
            responseConverted.setId(response.getDynamicFormQuestion().getId());
            responseConverted.setResponsibleId(responsibleId);
            responseConverted.setQuestion(response.getDynamicFormQuestion().getQuestion());
            if (response.getResponse().equals(TRUE)) {
                responseConverted.setAnswer("Sim");
            } else if (response.getResponse().equals(FALSE)) {
                responseConverted.setAnswer("Não");
            } else {
                responseConverted.setAnswer(response.getResponse());
            }
            responseConverted.setAttendanceId(attendanceId);
            logger.info("Inserindo pergunta respondida :: {}", responseConverted);
            formRepo.saveAndFlush(responseConverted);
        });
    }

    /**
     * Cria um evento de documento para anamnese
     *
     * @param request
     * @param document
     * @param requestOwner
     */
    @Transactional
    void createAnamneseDocumentEvent(Long attendanceId, NewDynamicFormAnsweredDTO request, MedicalRecordDTO medicalRecord,
                                     DigitalDocumentDTO document,
                                     UserInfoDTO requestOwner) {
        logger.info("Iniciando criação de Evento de atendimento para anamnese...");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setEventType(EventType.REPORT);
        event.setAttendanceId(attendanceId);
        event.getDocuments().add(document);
        event.setEventDateTime(LocalDateTime.now());
        event.setResponsible(getResponsibleFromUser(requestOwner));
        event.setAccommodation(medicalRecord.getLastAccommodation());
        logger.info("Evento processado, inserindo evento na base de dados...");

        try {
            attendanceService.createNewAttendanceEvent(event);
            logger.info("Evento inserido com sucesso!");
        } catch (Exception e) {
            logger.error("Faha ao tentar inserir evento de anamnese na base de dados!", e);
        }
    }

    /**
     * Busca o responsável pela requisição da anamnese baseado no usuário
     *
     * @param requestOwner
     * @return
     */
    private ResponsibleInfoDTO getResponsibleFromUser(UserInfoDTO requestOwner) {
        logger.info("Iniciando busca de responsável pelo usuário da requisição...");
        ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(requestOwner.getDocument().getValue());
        if (responsibleInformations.getId() == null)
            throw new HttpException(HttpStatus.FORBIDDEN, "Sem presmissão! Você não tem um profissional vinculado ao seu usuário.");
        logger.info("Profissional encontrado :: {}", responsibleInformations.getFullName());

        return responsibleInformations;
    }

    /**
     * Monta os parâmetros do relatório
     *
     * @param request
     * @param requestOwner
     * @return
     */
    private ReportParam getAnamnesisReportParams(Long attendanceId, NewDynamicFormAnsweredDTO request,
                                                 UserInfoDTO requestOwner) {
        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        InstituteDTO instituteDTO = instituteServive.getSettings();
        String logoBase64 = instituteDTO.getCustomerInfo().getLogoBase64();

        logger.info("Separando parâmetros e valores do relatório...");
        ReportParam params = new ReportParam();
        params.getParams().put(VISIT_ID, attendanceId);
        params.getParams().put(USER_ID, requestOwner.getId());
        params.getParams().put("DOC_TITLE", request.getDocumentTitle());
        params.getParams().put(REQUESTER_NAME, requestOwner.getFullName());
        params.getParams().put(HOSPITAL_LOGO, logoBase64);
        params.getParams().put(TODAY, new Date());

        logger.info("Parâmetros configurados e prontos para a criação do documento");

        return params;
    }

    /**
     * Valita as questões
     *
     * @param dynamicFormQuestion
     */
    private void validateQuestions(QuestionDTO dynamicFormQuestion) {
        logger.info("Iniciando validação da questão...");
        if (dynamicFormQuestion.getDynamicFormQuestion() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Seu questionário está com questão nula");
        else if (StringUtils.isNullOrEmpty(dynamicFormQuestion.getDynamicFormQuestion().getQuestion())
                || dynamicFormQuestion.getDynamicFormQuestion().getMetaType() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Seu questionário não está nulo mas está incompleto. Informe a questão e o tipo da questão");
        else if (StringUtils.isNullOrEmpty(dynamicFormQuestion.getResponse()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Você possui questão sem resposta, revise seu questionário");
        logger.info("Pergunta :: -> {}", dynamicFormQuestion.getDynamicFormQuestion().getQuestion());
        checkMetaTypes(dynamicFormQuestion);
    }

    /**
     * Valida os tipos das respostas
     *
     * @param dynamicFormQuestion
     */
    private void checkMetaTypes(QuestionDTO dynamicFormQuestion) {
        logger.info("Verificando meta tipos das respostas");
        switch (dynamicFormQuestion.getDynamicFormQuestion().getMetaType()) {
            case NUMBER:
                if (!StringUtils.isNumeric(dynamicFormQuestion.getResponse()))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor da resposta deve ser numérica");
                break;
            case BOOL:
                if (StringUtils.isNullOrEmpty(dynamicFormQuestion.getResponse())
                        || (!dynamicFormQuestion.getResponse().equalsIgnoreCase(TRUE)
                        && !dynamicFormQuestion.getResponse().equalsIgnoreCase(FALSE)))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor da resposta só pode ser true ou false");
                break;
            case STRING, TEXTAREA:
                break;
            default:
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Este metatipo é inválido para uma questão. Metatipos válidos -> number, bool ou string");
        }
    }

}
