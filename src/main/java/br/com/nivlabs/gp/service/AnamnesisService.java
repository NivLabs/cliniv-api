/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.dto.AccomodationDTO;
import br.com.nivlabs.gp.models.dto.AnamnesisDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.EventTypeDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.NewAnamnesisDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.AnamnesisRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Camada de serviço de anamenese do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class AnamnesisService implements GenericService {

    private static final String TODAY = "TODAY";
    private static final String HOSPITAL_LOGO = "HOSPITAL_LOGO";
    private static final String REQUESTER_NAME = "READER_NAME";
    private static final String VISIT_ID = "VISIT_ID";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final InputStream REPORT_SOURCE = ClassLoader.getSystemResourceAsStream("reports/Anamnese.jrxml");

    @Autowired
    private Logger logger;

    @Autowired
    private AnamnesisRepository dao;

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

    public Page<AnamnesisDTO> searchDTOPage(Pageable pageSettings) {
        Page<Anamnesis> page = dao.findAll(pageSettings);
        List<AnamnesisDTO> newPage = new ArrayList<>();
        page.getContent().forEach(domain -> newPage.add(domain.getAnamneseDTOFromDomain()));

        return new PageImpl<>(newPage, pageSettings, page.getTotalElements());
    }

    public List<Anamnesis> findByAttendance(Attendance attendance) {
        return dao.findByAttendance(attendance);
    }

    public Anamnesis findById(Long id) {
        try {
            return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Item da anamnese com o identificador %s não encontrado!", id)));

        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Anamnesis update(Long id, Anamnesis entity) {
        Anamnesis anamnese = findById(id);
        BeanUtils.copyProperties(entity, anamnese, "id");
        return anamnese;
    }

    public void deleteById(Long id) {
        Anamnesis anamnese = findById(id);
        dao.delete(anamnese);
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
        listOfAnamesesis.forEach(id -> dao.deleteById(id));
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
        attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());
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
            persist(item.getAnamnesesDomainFromDTO());
        });

        logger.info("Preparando documento de anamnese...");
        DigitalDocumentDTO document = reportService
                .createDocumentFromReport(request.getAttendanceId(), "Relatório de Anamnese", getAnamnesisReportParams(request, user),
                                          REPORT_SOURCE);

        createAnamneseDocumentEvent(request, document, user);

        return request;
    }

    /**
     * Cria um evento de documento para anamnese
     * 
     * @param request
     * @param document
     * @param requestOwner
     */
    private void createAnamneseDocumentEvent(NewAnamnesisDTO request, DigitalDocumentDTO document, UserInfoDTO requestOwner) {
        logger.info("Iniciando criação de Evento de atendimento para anamnese...");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setEventType(new EventTypeDTO(4L, "ANAMNESE", "Geração de Anamnese"));
        event.setAttendanceId(request.getAttendanceId());
        event.setDocuments(Arrays.asList(document));
        event.setEventDateTime(LocalDateTime.now());
        event.setObservations("Criação da anamnese");
        event.setResponsible(getResponsibleFromUser(requestOwner));
        event.setAccomodation(new AccomodationDTO(request.getAccomodationId()));
        logger.info("Evento processado, inserindo evento na base de dados...");

        try {
            eventService.persistNewAttendanceEvent(event);
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
        dao.findByAttendance(new Attendance(request.getAttendanceId())).stream().map(Anamnesis::getId).forEach(dao::deleteById);
    }

    /**
     * Busca o responsável pela requisição da anamnese baseado no usuário
     * 
     * @param requestOwner
     * @return
     */
    private ResponsibleDTO getResponsibleFromUser(UserInfoDTO requestOwner) {
        logger.info("Iniciando busca de responsável pelo usuário da requisição...");
        ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(requestOwner.getDocument().getValue());
        logger.info("Profissional encontrado :: {}", responsibleInformations.getFirstName());

        logger.info("Realizando processamento do profissional para a requisição de anamnese");
        ResponsibleDTO responsible = new ResponsibleDTO();
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
        params.getParams().put(REQUESTER_NAME, requestOwner.getFirstName() + " " + requestOwner.getLastName());
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
            case number:
                if (!StringUtils.isNumeric(anamnese.getResponse()))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor da resposta deve ser numérica");
                break;
            case bool:
                if (StringUtils.isNullOrEmpty(anamnese.getResponse())
                        || (!anamnese.getResponse().equalsIgnoreCase(TRUE)
                                && !anamnese.getResponse().equalsIgnoreCase(FALSE)))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor da resposta só pode ser true ou false");
                break;
            case string:
                break;
            default:
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Este metatipo é inválido para uma questão. Metatipos válidos -> number, bool ou string");
        }
    }

    /**
     * Insere na base de dados
     * 
     * @param entity
     * @return
     */
    public Anamnesis persist(Anamnesis entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
