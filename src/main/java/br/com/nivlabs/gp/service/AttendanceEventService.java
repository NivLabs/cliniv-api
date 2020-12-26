package br.com.nivlabs.gp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.AttendanceEvent_;
import br.com.nivlabs.gp.models.domain.EventType;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.EventTypeDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceEventRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Classe VisitEventService.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@Service
public class AttendanceEventService implements GenericService {

    private static final String SUCCESS_CONVERTION_MESSAGE = "Conversão concluída com sucesso!";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AttendanceEventRepository dao;
    @Autowired
    private DigitalDocumentService docService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private EventTypeService eventTypeService;
    @Autowired
    private ProcedureService procedureService;

    public Page<AttendanceEvent> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    public AttendanceEvent findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Evento de Visita com o ID: [%s] não encontrado", id)));
    }

    public AttendanceEvent update(Long id, AttendanceEvent entity) {
        AttendanceEvent auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, AttendanceEvent_.ID);
        return dao.save(auxEntity);
    }

    public void persistNewAttendanceEvent(NewAttendanceEventDTO request, UserOfSystem userFromSession) {
        if (request.getEventDateTime() == null) {
            request.setEventDateTime(LocalDateTime.now());
        }
        if (request.getResponsible() == null || request.getResponsible().getId() == null) {
            UserInfoDTO userInfo = userService.findByUserName(userFromSession.getUsername());
            request.setResponsible(getResponsibleFromUser(userInfo));
        }
        request.setEventType(eventTypeService.findById(request.getEventType().getId()).getEventTypeDTOFromDomain());

        logger.info("Iniciando o processo de criação de evento de atendimento");
        logger.info("Identificador do atendimento: {}", request.getAttendanceId());
        logger.info("Identificador do solicitante: {}", request.getResponsible().getId());
        logger.info("Identificador do tipo do evento: {}", request.getEventType().getId());
        logger.info("Data/Hora do evento: {}", request.getEventDateTime());

        AttendanceEvent newAttendanceEvent = new AttendanceEvent();
        newAttendanceEvent.setAttendance(new Attendance(request.getAttendanceId()));
        newAttendanceEvent.setEventDateTime(LocalDateTime.now());
        newAttendanceEvent.setEventType(convertEventType(request.getEventType()));
        newAttendanceEvent.setObservations(request.getObservations());
        newAttendanceEvent.setResponsible(convertResponsible(request.getResponsible()));
        newAttendanceEvent.setAccommodation(convertAccommodation(request.getAccommodation()));
        newAttendanceEvent.setTitle(request.getEventType().getDescription());
        if (request.getProcedure() != null && !StringUtils.isNullOrEmpty(request.getProcedure().getDescription()))
            newAttendanceEvent
                    .setTitle(request.getProcedure().getId().toString().concat(" - ").concat(request.getProcedure().getDescription()));
        newAttendanceEvent.setProcedure(convertProcedure(request.getProcedure()));

        Long newEventId = dao.save(newAttendanceEvent).getId();
        insertDocuments(newEventId, request.getDocuments());

    }

    /**
     * Busca o responsável pela criação de evento de atendimento
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

        logger.info("Realizando processamento do profissional para a criação de evento de atendimento");
        ResponsibleInfoDTO responsible = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(responsibleInformations, responsible);
        return responsible;
    }

    private Procedure convertProcedure(ProcedureDTO procedure) {
        logger.info("Convertendo informações de procedimento");

        Procedure procedureReturn = null;
        if (procedure != null && procedure.getId() != null) {
            procedureReturn = procedureService.findById(procedure.getId());
        }
        return procedureReturn;
    }

    private Accommodation convertAccommodation(AccommodationDTO accommodation) {
        logger.info("Convertendo informações de Sala ou Leito :: Identificador processado -> {}", accommodation.getId());
        Accommodation accommodationReturn = new Accommodation();
        BeanUtils.copyProperties(accommodation, accommodationReturn);
        logger.info(SUCCESS_CONVERTION_MESSAGE);
        return accommodationReturn;
    }

    private Responsible convertResponsible(ResponsibleInfoDTO responsible) {
        logger.info("Convertendo informações do responsável :: Identificador processado -> {}", responsible.getId());
        Responsible responsibleReturn = new Responsible();
        BeanUtils.copyProperties(responsible, responsibleReturn);
        logger.info(SUCCESS_CONVERTION_MESSAGE);
        return responsibleReturn;
    }

    private EventType convertEventType(EventTypeDTO eventType) {
        logger.info("Convertendo informações do tipo do evento :: Identificador processado -> {}", eventType.getId());
        EventType eventTypeReturn = new EventType();
        BeanUtils.copyProperties(eventType, eventTypeReturn);
        logger.info(SUCCESS_CONVERTION_MESSAGE);
        return eventTypeReturn;
    }

    private void insertDocuments(Long attendanceEventId, List<DigitalDocumentDTO> documents) {
        logger.info("Inserindo documentos digitais...");
        documents.forEach(doc -> {
            logger.info("Documento sendo processado :: Código do evento -> {} | Nome -> {}", attendanceEventId, doc.getName());
            doc.setAttendanceEventId(attendanceEventId);
            doc.setCreatedAt(LocalDateTime.now());
            docService.persist(doc);
            logger.info("Documentos criados com sucesso!");
        });
        logger.info("Processamento de documentos finalizado com sucesso!");
    }

}
