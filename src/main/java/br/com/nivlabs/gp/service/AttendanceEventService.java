package br.com.nivlabs.gp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.DigitalDocument;
import br.com.nivlabs.gp.models.domain.EventType;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.RoomOrBed;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.EventTypeDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.RoomOrBedDTO;
import br.com.nivlabs.gp.repository.AttendanceEventRepository;

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

    public Page<AttendanceEvent> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    public AttendanceEvent findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Evento de Visita com o ID: [%s] não encontrado", id)));
    }

    public AttendanceEvent update(Long id, AttendanceEvent entity) {
        AttendanceEvent auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    public void persistNewAttendanceEvent(NewAttendanceEventDTO request) {
        logger.info("Iniciando o processo de criação de evento de atendimento");
        logger.info("Identificador do atendimento: {}", request.getAttendanceId());
        logger.info("Identificador do solicitante: {}", request.getResponsible().getId());
        logger.info("Título do evento: {}", request.getEventType().getDescription());
        logger.info("Data/Hora do evento: {}", request.getEventDateTime());

        AttendanceEvent newAttendanceEvent = new AttendanceEvent();
        newAttendanceEvent.setAttendance(new Attendance(request.getAttendanceId()));
        newAttendanceEvent.setDocuments(convertDocuments(request.getDocuments()));
        newAttendanceEvent.setEventDateTime(LocalDateTime.now());
        newAttendanceEvent.setEventType(convertEventType(request.getEventType()));
        newAttendanceEvent.setObservations(request.getObservations());
        newAttendanceEvent.setResponsible(convertResponsible(request.getResponsible()));
        newAttendanceEvent.setRoomOrBed(convertRoomOrBed(request.getRoomOrBed()));
        newAttendanceEvent.setTitle(request.getEventType().getDescription());
        newAttendanceEvent.setProcedure(convertProcedure(request.getProcedure()));

        dao.save(newAttendanceEvent);

    }

    private Procedure convertProcedure(ProcedureDTO procedure) {
        logger.info("Convertendo informações de procedimento");

        Procedure procedureReturn = null;
        if (procedure != null) {
            procedureReturn = new Procedure();
            BeanUtils.copyProperties(procedure, procedureReturn);
        }
        return procedureReturn;
    }

    private RoomOrBed convertRoomOrBed(RoomOrBedDTO roomOrBed) {
        logger.info("Convertendo informações de Sala ou Leito :: Identificador processado -> {}", roomOrBed.getId());
        RoomOrBed roomOrBedReturn = new RoomOrBed();
        BeanUtils.copyProperties(roomOrBed, roomOrBedReturn);
        logger.info(SUCCESS_CONVERTION_MESSAGE);
        return roomOrBedReturn;
    }

    private Responsible convertResponsible(ResponsibleDTO responsible) {
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

    private List<DigitalDocument> convertDocuments(List<DigitalDocumentDTO> documents) {
        logger.info("Convertendo documentos digitais...");
        List<DigitalDocument> returnList = new ArrayList<>();
        documents.forEach(doc -> {
            logger.info("Documento sendo processado :: Identificador -> {} | Nome -> {}", doc.getId(), doc.getName());
            DigitalDocument docToList = new DigitalDocument();
            BeanUtils.copyProperties(doc, docToList);
            returnList.add(docToList);
        });

        logger.info(SUCCESS_CONVERTION_MESSAGE);

        return returnList;
    }

}
