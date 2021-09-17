/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.DynamicFormFilters;
import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.domain.DynamicFormAnswered;
import br.com.nivlabs.gp.models.domain.DynamicFormQuestion;
import br.com.nivlabs.gp.models.domain.DynamicFormQuestion_;
import br.com.nivlabs.gp.models.domain.DynamicForm_;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.NewDynamicFormAnsweredDTO;
import br.com.nivlabs.gp.models.dto.QuestionDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.DynamicFormAnsweredRepository;
import br.com.nivlabs.gp.repository.DynamicFormQuestionRepository;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Camada de serviço de formulários dinâmicos da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class DynamicFormService implements BaseService {

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
    private DynamicFormRepository dynamicFormDao;
    @Autowired
    private DynamicFormQuestionRepository dynamicFormQuestionDao;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private InstituteService instituteServive;

    @Autowired
    private AttendanceEventService eventService;

    @Autowired
    private UserService userSerive;

    @Autowired
    private ResponsibleService responsibleService;

    @Autowired
    private DynamicFormAnsweredRepository formRepo;

    /**
     * Realiza a exclusão física do formulário dinâmico
     * 
     * @param id Identificador único do formulário dinâmico
     */
    public void deleteFormById(Long id) {
        logger.info("Iniciando exclusão de formulário dinâmico :: {}", id);
        DynamicForm entity = dynamicFormDao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Formulários dinâmico com o identificador %s não encontrado.", id)));
        logger.info("Formulário encontrado :: {} | {} ", entity.getId(), entity.getTitle());
        dynamicFormDao.delete(entity);
        logger.info("Formulário excluído com sucesso!");
    }

    /**
     * Cria um novo questionário respondido de anamnese
     * 
     * @param request
     * @return
     */
    public NewDynamicFormAnsweredDTO newDynamicFormAnswered(Long attendanceId, NewDynamicFormAnsweredDTO request, String requestOwner) {
        logger.info("Iniciando o preenchimento de um novo formulário dinâmico...");
        MedicalRecordDTO medicalRecord = attendanceService.findMedicalRecordByAttendanceId(attendanceId);
        logger.info("Verificando o usuário da solicitação");
        UserInfoDTO user = userSerive.findByUserName(requestOwner);

        logger.info("Validando respostas...");
        request.getListOfResponse().forEach(this::validateQuestions);

        try {
            createAnsweredForm(user.getId(), attendanceId, request);
            logger.info("Preparando formulário dinâmico...");

            DigitalDocumentDTO document = reportService
                    .createDocumentFromReport(attendanceId, request.getDocumentTitle(),
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
    private void createAnsweredForm(Long responsibleId, Long attendanceId, NewDynamicFormAnsweredDTO request) {
        logger.info("Iniciando processo de inserção de respostas do formulário dinâmico :: {}", request.getListOfResponse());
        logger.info("Removendo respostas anteriores...");
        formRepo.deleteByResponsibleId(responsibleId);
        logger.info("Base limpa. Iniciando processo de inserção de novas respostas...");
        request.getListOfResponse().forEach(response -> {
            DynamicFormAnswered responseConverted = new DynamicFormAnswered();
            responseConverted.setId(response.getDynamicFormQuestion().getId());
            responseConverted.setResponsibleId(responsibleId);
            responseConverted.setQuestion(response.getDynamicFormQuestion().getQuestion());
            responseConverted.setAnswer(response.getResponse()
                    .equals("true") ? "Sim" : response.getResponse().equals("false") ? "Não" : response.getResponse());
            responseConverted.setAttendanceId(attendanceId);
            logger.info("Inserindo pergunta respondida:: {}", responseConverted);
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
    private void createAnamneseDocumentEvent(Long attendanceId, NewDynamicFormAnsweredDTO request, MedicalRecordDTO medicalRecord,
                                             DigitalDocumentDTO document,
                                             UserInfoDTO requestOwner) {
        logger.info("Iniciando criação de Evento de atendimento para anamnese...");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setEventType(EventType.REPORT);
        event.setAttendanceId(attendanceId);
        event.setDocuments(Arrays.asList(document));
        event.setEventDateTime(LocalDateTime.now());
        event.setObservations(request.getDocumentTitle());
        event.setResponsible(getResponsibleFromUser(requestOwner));
        event.setAccommodation(medicalRecord.getLastAccommodation());
        logger.info("Evento processado, inserindo evento na base de dados...");

        try {
            eventService.persistNewAttendanceEvent(event, null);
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

        logger.info("Realizando processamento do profissional para a requisição de anamnese");
        ResponsibleInfoDTO responsible = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(responsibleInformations, responsible);
        return responsible;
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
            case STRING:
            case TEXTAREA:
                break;
            default:
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Este metatipo é inválido para uma questão. Metatipos válidos -> number, bool ou string");
        }
    }

    /**
     * Busca um formulário de anamnese pelo identificador do mesmo
     * 
     * @param id Identificador único do formulário de Anamnese
     * @return Formulário de Anamnese com título e perguntas
     */
    public DynamicFormDTO findFormById(Long id) {
        logger.info("Iniciando processo de busca de formulário dinâmico pelo identificador :: {}", id);

        DynamicForm objFromDb = dynamicFormDao.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Formulário com o identificador %s não encontrado", id)));
        logger.info("Formulário encontrado :: {} | {} :: Iniciando processo de conversão...", objFromDb.getId(), objFromDb.getTitle());

        DynamicFormDTO response = new DynamicFormDTO();

        BeanUtils.copyProperties(objFromDb, response, DynamicForm_.QUESTIONS);
        logger.info("Convertendo as questões do formulário :: Total de questões: {}", objFromDb.getQuestions().size());
        objFromDb.getQuestions().forEach(question -> {
            DynamicFormQuestionDTO convertedQuestion = new DynamicFormQuestionDTO();
            BeanUtils.copyProperties(question, convertedQuestion);
            logger.info("Adicionando questão :: {}", convertedQuestion);
            response.getQuestions().add(convertedQuestion);
        });
        logger.info("Conversão finalizada, devolvendo resposta...");
        return response;
    }

    /**
     * Busca uma página de formulárioes de anamnese
     * 
     * @param pageSettings Configurações de paginação
     * @return Página de formulários de anamnese
     */
    public Page<DynamicFormDTO> findPageOfDymicaForm(DynamicFormFilters filters, Pageable pageSettings) {
        logger.info("Iniciando busca de formulários dinâmicos..");
        return dynamicFormDao.resumedList(filters, pageSettings);
    }

    /**
     * Cria um formulário dinâmico na aplicação
     * 
     * @param request Informações para criação de formulário dinâmico
     * @return Formulário dinâmico criado
     */
    public DynamicFormDTO create(DynamicFormDTO request) {
        logger.info("Inicianco processo de criação de formulário dinâmico :: {}", request.getTitle());
        DynamicForm entity = new DynamicForm();
        entity.setTitle(request.getTitle());
        entity = dynamicFormDao.saveAndFlush(entity);
        request.setId(entity.getId());
        logger.info("Formulário criado com sucesso :: {} | {}", request.getId(), request.getTitle());
        return request;
    }

    /**
     * Atualiza informações do formulário dinâmico
     * 
     * @param id Identificador único do formulário
     * @param request Informações a serem atualizadas
     * @return Formulário atualizado
     */
    public DynamicFormDTO update(Long id, DynamicFormDTO request) {
        logger.info("Inicianco processo de atualização de formulário dinâmico :: {} | {} ", request.getId(), request.getTitle());
        DynamicForm entity = dynamicFormDao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Formulário dinâmico com o identificador %s não encontrado.", id)));
        BeanUtils.copyProperties(request, entity, DynamicForm_.ID, DynamicForm_.QUESTIONS);
        entity = dynamicFormDao.saveAndFlush(entity);
        BeanUtils.copyProperties(entity, request);
        logger.info("Formulário atualizado com sucesso :: {} | {}", request.getId(), request.getTitle());
        return request;
    }

    /**
     * Deleta uma questão do formulário dinâmico
     * 
     * @param id Identificador único da questão do formulário dinâmico
     */
    public void deleteQuestionById(Long id) {
        logger.info("Iniciando processo de remoção de questão do formulário dinâmico :: {}", id);
        DynamicFormQuestion entity = dynamicFormQuestionDao.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Questão com o identificador %s não encontrado.", id)));
        logger.info("Questão encontrada :: Formulário -> {} :: Questão -> {}", entity.getForm().getTitle(), entity.getQuestion());
        dynamicFormQuestionDao.delete(entity);
        logger.info("Remoção realizada com sucesso!");
    }

    /**
     * Cria uma questão para o formulário dinâmico
     * 
     * @param idForm Identificador único do formulário dinâmico que terá uma questão adicionada
     * @param request Questão que será criada no formulário
     * @return Questão criada
     */
    public DynamicFormQuestionDTO createQuestion(Long idForm, DynamicFormQuestionDTO request) {
        logger.info("Iniciando processo de criação de questão do formulário dinâmico :: {} | {}", request.getQuestion(),
                    request.getMetaType());
        DynamicForm form = dynamicFormDao.findById(idForm).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String
                .format("Formulários dinâmico com o identificador %s não encontrado, não será possível criar uma pergunta para o mesmo.",
                        idForm)));
        logger.info("Formulário localizado :: {} | {}", idForm, form.getTitle());

        logger.info("Criando entidade para criação da questão...");
        DynamicFormQuestion question = new DynamicFormQuestion();
        question.setForm(new DynamicForm(idForm, null, null));
        question.setMetaType(request.getMetaType());
        question.setQuestion(request.getQuestion());
        dynamicFormQuestionDao.saveAndFlush(question);

        request.setId(question.getId());

        logger.info("Questão adicionada ao formulário :: FormId -> {} + QuestId -> {}", idForm, request.getId());

        return request;
    }

    /**
     * Atualiza uma questão do formulário dinâmico
     * 
     * @param id Identificador único da questão do formulário
     * @param request Objeto com dados modificados da questão do formulário
     * @return Questão atualizada
     */
    public DynamicFormQuestionDTO updateQuestion(Long id, DynamicFormQuestionDTO request) {
        request.setId(id);
        logger.info("Iniciando processo de atualização de questão de formulário :: {} | {} | {}", request.getId(), request.getQuestion(),
                    request.getMetaType());
        DynamicFormQuestion entity = dynamicFormQuestionDao.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Questão com o identificador %s não encontrado.", id)));
        logger.info("Formulário à ser alterado :: {} | {} | {}", entity.getId(), entity.getQuestion(), entity.getMetaType());
        BeanUtils.copyProperties(request, entity, DynamicFormQuestion_.ID);
        dynamicFormQuestionDao.saveAndFlush(entity);
        logger.info("Formulário alterado com sucesso!");
        return request;
    }

}
