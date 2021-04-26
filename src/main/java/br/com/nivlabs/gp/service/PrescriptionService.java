package br.com.nivlabs.gp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.ItemPrescriptionIdPK;
import br.com.nivlabs.gp.models.domain.Prescription;
import br.com.nivlabs.gp.models.domain.PrescriptionItem;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.PrescriptionInfoDTO;
import br.com.nivlabs.gp.models.dto.PrescriptionItemDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.PrescriptionRepository;

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
    private static final String PRESCRIPTION_ID = "PRESC_ID";
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

    @Autowired
    private PrescriptionRepository dao;

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
        ResponsibleInfoDTO responsible = getResponsibleFromUser(user);

        Prescription insetedPrescription = insertPrescription(request, responsible, medicalRecord);

        logger.info("Iniciando criação do documento digital da prescrição");
        try {
            DigitalDocumentDTO document = reportService.createDocumentFromReport(request.getAttendanceId(), "Prescrição Médica",
                                                                                 getReportParam(insetedPrescription.getId(), user),
                                                                                 new ClassPathResource(REPORT_SOURCE).getInputStream());
            createDocumentEvent(request, document, responsible, medicalRecord);
        } catch (IOException e) {
            logger.error("Falha ao gerar documento de evolução", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento de evolução");
        }

        return null;
    }

    /**
     * Insere uma prescrição médica na base de dados para controle da aplicação
     * 
     * @param request Requisição de nova prescrição médica
     * @param responsible Responsável pela solicitação
     * @param medicalRecord Atendimento em questão
     */
    private Prescription insertPrescription(PrescriptionInfoDTO request, ResponsibleInfoDTO responsible, MedicalRecordDTO medicalRecord) {
        logger.info("Iniciando processo inserção de prescrição à base de dados... Processando informações...");
        Prescription newPrescription = new Prescription();
        newPrescription.setAttendance(new Attendance(medicalRecord.getId()));
        newPrescription.setResponsible(new Responsible(responsible.getId()));
        newPrescription.setDatetimeInit(request.getDatetimeInit());
        newPrescription.setDatetimeEnd(request.getDatetimeEnd());
        logger.info("Cabeçalho da prescrição :: {}", newPrescription);

        newPrescription.setItems(convertItems(request.getItems(), newPrescription));

        logger.info("Inserindo prescrição à base de dados...");
        dao.saveAndFlush(newPrescription);
        logger.info("Precrição criada com sucesso!");
        return newPrescription;
    }

    /**
     * Converte itens da prescrição vindos da requisição em itens da prescrição no modelo relacional
     * 
     * @param items Itens da prescrição
     * @param medicalRecord Atendimento
     * @return Lista de itens da prescrição convertida
     */
    private List<PrescriptionItem> convertItems(List<PrescriptionItemDTO> items, Prescription prescription) {
        if (items == null || items.isEmpty()) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Prescrição inválida, nenhum item associado!");
        }
        List<PrescriptionItem> convertedItems = new ArrayList<>();
        logger.info("Iniciando processo de conversão de itens da prescrição...");
        items.forEach(item -> {
            logger.info("Item :: {}", item);

            PrescriptionItem convertedItem = new PrescriptionItem();
            convertedItem.setId(new ItemPrescriptionIdPK(item.getSequential(), prescription.getId()));
            convertedItem.setDescription(item.getDescription());
            convertedItem.setPrescription(prescription);
            convertedItem.setDosage(item.getDosage());
            convertedItem.setObservations(item.getObservations());
            convertedItem.setRouteOfAdministration(item.getRouteOfAdministration());
            convertedItem.setTimeInterval(item.getTimeInterval());
            convertedItem.setTimeIntervalType(item.getTimeIntervalType());
            convertedItem.setUnitOfMeasurement(item.getUnitOfMeasurement());

            convertedItems.add(convertedItem);
        });
        logger.info("Conversão concluída, total de itens convertidos :: {}", convertedItems.size());
        return convertedItems;
    }

    private ReportParam getReportParam(Long prescriptionId, UserInfoDTO user) {
        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        InstituteDTO instituteDTO = instituteServive.getSettings();
        String logoBase64 = instituteDTO.getCustomerInfo().getLogoBase64();

        logger.info("Separando parâmetros e valores do relatório...");
        ReportParam params = new ReportParam();
        params.getParams().put(PRESCRIPTION_ID, prescriptionId);
        params.getParams().put("DOC_TITLE", "PRESCRIÇÃO");
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
    private void createDocumentEvent(PrescriptionInfoDTO request, DigitalDocumentDTO document, ResponsibleInfoDTO requestOwner,
                                     MedicalRecordDTO medicalRecord) {
        logger.info("Iniciando criação de Evento de atendimento para prescrição...");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setEventType(EventType.PRESCRIPTION);
        event.setAttendanceId(request.getAttendanceId());
        event.setDocuments(Arrays.asList(document));
        event.setEventDateTime(LocalDateTime.now());
        event.setObservations("Criação da prescrição");
        event.setResponsible(requestOwner);
        event.setAccommodation(medicalRecord.getLastAccommodation());
        logger.info("Evento processado, inserindo evento na base de dados...");

        eventService.persistNewAttendanceEvent(event, null);
        logger.info("Evento inserido com sucesso!");
    }

    /**
     * Busca o responsável pela requisição da prescrição baseado no usuário
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

        logger.info("Realizando processamento do profissional para a requisição de prescrição");
        ResponsibleInfoDTO responsible = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(responsibleInformations, responsible);
        return responsible;
    }
}
