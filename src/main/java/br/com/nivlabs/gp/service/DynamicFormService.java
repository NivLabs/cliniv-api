/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.Anamnesis_;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.domain.DynamicForm_;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.AnamnesisDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAnamnesisDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.repository.DynamicFormResponseRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Camada de serviço de formulários dinâmicos da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class DynamicFormService implements GenericService {

    private static final String TODAY = "TODAY";
    private static final String HOSPITAL_LOGO = "HOSPITAL_LOGO";
    private static final String REQUESTER_NAME = "READER_NAME";
    private static final String VISIT_ID = "VISIT_ID";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final String REPORT_PATH = "reports/Anamnese.jrxml";

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormResponseRepository dynamicFormResponseDao;
    @Autowired
    private DynamicFormRepository dynamicFormDao;

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

    public List<Anamnesis> findByAttendance(Attendance attendance) {
        return dynamicFormResponseDao.findByAttendance(attendance);
    }

    public Anamnesis findById(Long id) {
        try {
            return dynamicFormResponseDao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Item da anamnese com o identificador %s não encontrado!", id)));

        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Anamnesis update(Long id, Anamnesis entity) {
        Anamnesis anamnese = findById(id);
        BeanUtils.copyProperties(entity, anamnese, Anamnesis_.ID);
        return anamnese;
    }

    public void deleteById(Long id) {
        Anamnesis anamnese = findById(id);
        dynamicFormResponseDao.delete(anamnese);
    }

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
     * Deleta um questionário respondido de anamnese baseado no identificador do atendimento
     * 
     * @param attendanceId
     */
    public void deleteAnamnesisFromAttendance(Long attendanceId) {
        logger.info("Solicitação de exclução de anamnese recebida, código do atendimento :: {}", attendanceId);
        List<Long> listOfAnamesesis = findByAttendance(new Attendance(attendanceId)).stream().map(Anamnesis::getId)
                .collect(Collectors.toList());
        if (listOfAnamesesis.isEmpty())
            logger.info("Não há anamnese para exclusão");
        else
            logger.info("Foram encontradas um total de {} questões respondidas, inicializando exclusão dos itens...",
                        listOfAnamesesis.size());
        listOfAnamesesis.forEach(id -> dynamicFormResponseDao.deleteById(id));
        logger.info("Processo finalizado com sucesso");

    }

    /**
     * Cria um novo questionário respondido de anamnese
     * 
     * @param request
     * @return
     */
    public NewAnamnesisDTO newAnamnesisResponse(NewAnamnesisDTO request, String requestOwner) {
        logger.info("Iniciando o preenchimento de um novo questionário de anamnese...");
        MedicalRecordDTO medicalRecord = attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());
        if (request.getAccommodationId() == null && medicalRecord.getLastAccommodation() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O atendimento atual não possui acomodação, informe a acomodação em que a anamnese está sendo realizada");
        }
        if (!findByAttendance(new Attendance(request.getAttendanceId())).isEmpty()) {
            logger.warn("Este atendimento já possui um questionário de anamnese respondido, deletando formulário anterior para sobreescrita..");
            undoProcess(request);
            logger.info("Formulário anterio excluído, iniciando um novo processo de anamnese...");
        }
        logger.info("Verificando o usuário da solicitação");
        UserInfoDTO user = userSerive.findByUserName(requestOwner);

        logger.info("Processand respostas");
        request.getListOfResponse().forEach(item -> {
            validateQuestions(item);
            item.setAttendanceId(request.getAttendanceId());
        });
        try {

            logger.info("Preparando documento de anamnese...");

            DigitalDocumentDTO document = reportService
                    .createDocumentFromReport(request.getAttendanceId(), "Relatório de Anamnese", getAnamnesisReportParams(request, user),
                                              new ClassPathResource(REPORT_PATH).getInputStream());
            createAnamneseDocumentEvent(request, medicalRecord, document, user);
        } catch (IOException e) {
            logger.error("Falha ao gerar documento de Anamnese", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento de anamnese");
        }

        return request;
    }

    /**
     * Cria um evento de documento para anamnese
     * 
     * @param request
     * @param document
     * @param requestOwner
     */
    private void createAnamneseDocumentEvent(NewAnamnesisDTO request, MedicalRecordDTO medicalRecord, DigitalDocumentDTO document,
                                             UserInfoDTO requestOwner) {
        logger.info("Iniciando criação de Evento de atendimento para anamnese...");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setEventType(EventType.ANAMESIS);
        event.setAttendanceId(request.getAttendanceId());
        event.setDocuments(Arrays.asList(document));
        event.setEventDateTime(LocalDateTime.now());
        event.setObservations("Criação da anamnese");
        event.setResponsible(getResponsibleFromUser(requestOwner));
        if (request.getAccommodationId() == null)
            event.setAccommodation(medicalRecord.getLastAccommodation());
        else
            event.setAccommodation(new AccommodationDTO(request.getAccommodationId()));
        logger.info("Evento processado, inserindo evento na base de dados...");

        try {
            eventService.persistNewAttendanceEvent(event, null);
            logger.info("Evento inserido com sucesso!");
        } catch (Exception e) {
            logger.error("Faha ao tentar inserir evento de anamnese na base de dados!", e);
            logger.info("Desfazendo registros de Anamnese...");
            undoProcess(request);
            logger.info("Desfazimento concluído com sucesso!");
        }
    }

    /**
     * Desfaz o processamento de registros de anamnese do atendimento
     * 
     * @param request
     */
    private void undoProcess(NewAnamnesisDTO request) {
        dynamicFormResponseDao.findByAttendance(new Attendance(request.getAttendanceId())).stream().map(Anamnesis::getId)
                .forEach(dynamicFormResponseDao::deleteById);
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
    private ReportParam getAnamnesisReportParams(NewAnamnesisDTO request, UserInfoDTO requestOwner) {
        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        InstituteDTO instituteDTO = instituteServive.getSettings();
        String logoBase64 = instituteDTO.getCustomerInfo().getLogoBase64();

        logger.info("Separando parâmetros e valores do relatório...");
        ReportParam params = new ReportParam();
        params.getParams().put(VISIT_ID, request.getAttendanceId());
        params.getParams().put("DOC_TITLE", "RELATÓRIO DE ANAMNESE DO PACIENTE");
        params.getParams().put(REQUESTER_NAME, requestOwner.getFullName());
        params.getParams().put(HOSPITAL_LOGO, logoBase64);
        params.getParams().put(TODAY, new Date());

        logger.info("Parâmetros configurados e prontos para a criação do documento");

        return params;
    }

    /**
     * Valita as questões
     * 
     * @param anamnese
     */
    private void validateQuestions(AnamnesisDTO anamnese) {
        logger.info("Iniciando validação da questão...");
        if (anamnese.getAnamnesisItem() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Seu questionário está com questão nula");
        else if (StringUtils.isNullOrEmpty(anamnese.getAnamnesisItem().getQuestion())
                || anamnese.getAnamnesisItem().getMetaType() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Seu questionário não está nulo mas está incompleto. Informe a questão e o tipo da questão");
        else if (StringUtils.isNullOrEmpty(anamnese.getResponse()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Você possui questão sem resposta, revise seu questionário");
        logger.info("Pergunta :: -> {}", anamnese.getAnamnesisItem().getQuestion());
        checkMetaTypes(anamnese);
    }

    /**
     * Valida os tipos das respostas
     * 
     * @param anamnese
     */
    private void checkMetaTypes(AnamnesisDTO anamnese) {
        logger.info("Verificando meta tipos das respostas");
        switch (anamnese.getAnamnesisItem().getMetaType()) {
            case NUMBER:
                if (!StringUtils.isNumeric(anamnese.getResponse()))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor da resposta deve ser numérica");
                break;
            case BOOL:
                if (StringUtils.isNullOrEmpty(anamnese.getResponse())
                        || (!anamnese.getResponse().equalsIgnoreCase(TRUE)
                                && !anamnese.getResponse().equalsIgnoreCase(FALSE)))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor da resposta só pode ser true ou false");
                break;
            case STRING:
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
        logger.info("Iniciando processo de busca de formulário de anamnese pelo identificador :: {}", id);

        DynamicForm objFromDb = dynamicFormDao.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Formulário com o identificador %s não encontrado", id)));
        logger.info("Formulário encontrado :: {} | {} :: Iniciando processo de conversão...", objFromDb.getId(), objFromDb.getTitle());

        DynamicFormDTO response = new DynamicFormDTO();

        BeanUtils.copyProperties(objFromDb, response, Anamnesis_.QUESTION);
        logger.info("Convertendo as questões do formulário :: Total de questões: {}", objFromDb.getQuestions().size());
        objFromDb.getQuestions().forEach(question -> {
            DynamicFormQuestionDTO convertedQuestion = new DynamicFormQuestionDTO();
            BeanUtils.copyProperties(question, convertedQuestion);
            logger.info("Adicionando questão :: {}", convertedQuestion);
            response.getQuestions().add(convertedQuestion);
        });
        logger.info("Conversão finalizada, devolvendo resposta para o client.");
        return response;
    }

    /**
     * Busca uma página de formulárioes de anamnese
     * 
     * @param pageSettings Configurações de paginação
     * @return Página de formulários de anamnese
     */
    public Page<DynamicFormDTO> findPageOfDymicaForm(DynamicFormFilters filters, Pageable pageSettings) {
        logger.info("Iniciando busca de formulários de Anamnese..");
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

    public void deleteQuestionById(Long id) {
        // TODO Auto-generated method stub

    }

    public DynamicFormQuestionDTO createQuestion(Long idForm, DynamicFormQuestionDTO request) {
        // TODO Auto-generated method stub
        return null;
    }

    public DynamicFormQuestionDTO updateQuestion(Long id, DynamicFormQuestionDTO request) {
        // TODO Auto-generated method stub
        return null;
    }

}
