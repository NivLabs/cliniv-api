package br.com.nivlabs.gp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.Evolution;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.AccommodationRepository;
import br.com.nivlabs.gp.repository.EvolutionRepository;

/**
 * Camada de serviço para manipulação de evoluções clínicas
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class EvolutionService implements GenericService {

    private static final String EVOLUTION_REPORT_NAME = "Relatório de Evolução Clínica";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String TODAY = "TODAY";
    private static final String HOSPITAL_LOGO = "HOSPITAL_LOGO";
    private static final String READER_NAME = "READER_NAME";
    private static final String VISIT_ID = "VISIT_ID";
    private static final String DESCIPTION = "DESCRICAO";
    private static final String REPORT_SOURCE = "reports/Evolucao.jrxml";

    @Autowired
    private EvolutionRepository repository;

    @Autowired
    private AttendanceEventService attendanceEventService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponsibleService responsibleService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private InstituteService instituteServive;

    /**
     * Insere uma nova evolução clínica do paciente
     * 
     * @param request
     */
    public EvolutionInfoDTO persist(EvolutionInfoDTO request, String userFromSession) {
        logger.info("Iniciando a adição de uma evolução clínica ao sistema...\nBuscando pelo atendimento informado na requisição :: {}",
                    request.getAttendanceId());
        MedicalRecordDTO medicalRecord = attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());

        logger.info("Atendimento encontrado para o paciente {}.", medicalRecord.getFullName());
        LocalDateTime currentTime = LocalDateTime.now();
        logger.info("Hora da solicitaçõ do evento de Evolução :: {}", currentTime);

        Evolution evolution = new Evolution();
        evolution.setAttendance(new Attendance(request.getAttendanceId()));
        evolution.setDescription(request.getDescription());
        evolution.setDatetime(currentTime);

        request.setDatetime(evolution.getDatetime());
        createAttendanceEvent(request, medicalRecord, userFromSession);

        logger.info("Inserindo evolução clínica na base de dados...");
        repository.save(evolution);

        request.setId(evolution.getId());
        logger.info("Evolução clínica adicionada com sucesso :: Identificador :: {}", request.getId());

        return request;
    }

    /**
     * Busca uma evolução clínica
     * 
     * @param id
     * @return
     */
    public EvolutionInfoDTO findById(Long id) {
        return null;
    }

    /**
     * Cria o evento de atendimento para adição de Evolução clínica
     * 
     * @param evolution
     */
    private void createAttendanceEvent(EvolutionInfoDTO request, MedicalRecordDTO medicalRecord, String userFromSession) {
        logger.info("Iniciando processo de criação de evento do atendimento para Evolução clínica");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();

        event.setAttendanceId(request.getAttendanceId());
        event.setEventDateTime(request.getDatetime());
        event.setEventType(EventType.EVOLUTION);
        setAccommodationIntoAttendanceEvent(request, medicalRecord, event);

        UserInfoDTO userInfo = userService.findByUserName(userFromSession);
        event.setResponsible(getResponsibleFromUser(userInfo));
        event.setObservations(EventType.EVOLUTION.getDescription());

        DigitalDocumentDTO document;

        try {
            document = reportService
                    .createDocumentFromReport(request.getAttendanceId(), EVOLUTION_REPORT_NAME,
                                              getEvolutionReportParams(request, userInfo),
                                              new ClassPathResource(REPORT_SOURCE).getInputStream());
            event.getDocuments().add(document);
        } catch (IOException e) {
            logger.error("Falha ao gerar documento de evolução", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento de evolução");
        }

        attendanceEventService.persistNewAttendanceEvent(event, null);
        logger.info("Evento de Atendimento para Evolução clínica criado com sucesso!");

    }

    /**
     * Monta os parâmetros do relatório
     * 
     * @param request
     * @param requestOwner
     * @return
     */
    private ReportParam getEvolutionReportParams(EvolutionInfoDTO request, UserInfoDTO requestOwner) {
        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        InstituteDTO instituteDTO = instituteServive.getSettings();
        String logoBase64 = instituteDTO.getCustomerInfo().getLogoBase64();

        logger.info("Separando parâmetros e valores do relatório...");
        ReportParam params = new ReportParam();
        params.getParams().put(VISIT_ID, request.getAttendanceId());
        params.getParams().put("DOC_TITLE", "RELATÓRIO DE EVOLUÇÃO CLÍNICA DO PACIENTE");
        params.getParams().put(READER_NAME, requestOwner.getFullName());
        params.getParams().put(HOSPITAL_LOGO, logoBase64);
        params.getParams().put(TODAY, new Date());
        params.getParams().put(DESCIPTION, request.getDescription());

        logger.info("Parâmetros configurados e prontos para a criação do documento");

        return params;
    }

    /**
     * Adiciona uma acomodação no evento do atendimento
     * 
     * @param request
     * @param medicalRecord
     * @param event
     */
    private void setAccommodationIntoAttendanceEvent(EvolutionInfoDTO request, MedicalRecordDTO medicalRecord,
                                                     NewAttendanceEventDTO event) {
        logger.info("Verificando acomodação do evento...");
        if (request.getAccommodationId() != null) {
            logger.info("A acomodação foi informada via requisição :: Identificador informado :: {} :: Iniciando uma busca pelo mesmo...",
                        request.getAccommodationId());
            Accommodation accommodation = accommodationRepository.findById(request.getAccommodationId())
                    .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                            String.format("Acomodação com código %s não encontrada", request.getAccommodationId())));
            logger.info("A acomodação informada na requisição foi encontrada e adicionada ao evento :: {}", accommodation.getDescription());
            event.setAccommodation(new AccommodationDTO(request.getAccommodationId()));
        } else if (medicalRecord.getLastAccommodation() != null) {
            logger.info("Nenhuma acomodação foi informada na requisição, buscando última acomodação do paciente...");
            AccommodationDTO currentAccommodation = medicalRecord.getLastAccommodation();
            logger.info("A última acomodação do paciente foi adicionada ao evento do atendimento (Evolução) :: Identificador {} | Descrição {}",
                        currentAccommodation.getId(),
                        currentAccommodation.getDescription());
            event.setAccommodation(new AccommodationDTO(currentAccommodation.getId()));
        } else {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Nenhuma acomodação foi informada na requisição e não há registro de acomodações para o paciente.");
        }
    }

    /**
     * Busca o responsável pela criação da evolução clínica
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

        logger.info("Realizando processamento do profissional para a requisição de evolução clínica");
        ResponsibleInfoDTO responsible = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(responsibleInformations, responsible);
        return responsible;
    }

}
