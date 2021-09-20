package br.com.nivlabs.gp.service.attendance.business;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceEventRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.DigitalDocumentService;
import br.com.nivlabs.gp.service.ProcedureService;
import br.com.nivlabs.gp.service.ResponsibleService;
import br.com.nivlabs.gp.service.UserService;
import br.com.nivlabs.gp.util.SecurityContextUtil;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * 
 * Camada de negócio relacionada à processo de criação de evento de atendimento
 * 
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class CreateAttendanceEventBusinessHandler implements BaseBusinessHandler {

    private static final String SUCCESS_CONVERTION_MESSAGE = "Conversão concluída com sucesso!";

    @Autowired
    private Logger logger;

    @Autowired
    private AttendanceEventRepository attendanceEventRepo;
    @Autowired
    private DigitalDocumentService docService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private ProcedureService procedureService;

    public void create(NewAttendanceEventDTO request) {
        if (request.getEventDateTime() == null) {
            request.setEventDateTime(LocalDateTime.now());
        }

        if (request.getResponsible() == null || request.getResponsible().getId() == null) {
            UserInfoDTO userInfo = userService.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername());
            request.setResponsible(getResponsibleFromUser(userInfo));
        }

        logger.info("Iniciando o processo de criação de evento de atendimento");
        logger.info("Identificador do atendimento: {}", request.getAttendanceId());
        logger.info("Identificador do solicitante: {}", request.getResponsible().getId());
        logger.info("Identificador do tipo do evento: {}", request.getEventType());
        logger.info("Data/Hora do evento: {}", request.getEventDateTime());

        AttendanceEvent newAttendanceEvent = new AttendanceEvent();
        newAttendanceEvent.setAttendance(new Attendance(request.getAttendanceId()));
        newAttendanceEvent.setEventDateTime(LocalDateTime.now());
        newAttendanceEvent.setEventType(request.getEventType());
        newAttendanceEvent.setObservations(request.getObservations());
        newAttendanceEvent.setResponsible(convertResponsible(request.getResponsible()));
        newAttendanceEvent.setAccommodation(convertAccommodation(request.getAccommodation()));
        newAttendanceEvent.setTitle(request.getEventType().getDescription());
        if (request.getProcedure() != null && !StringUtils.isNullOrEmpty(request.getProcedure().getDescription())) {
            newAttendanceEvent
                    .setTitle(request.getProcedure().getId().toString().concat(" - ").concat(request.getProcedure().getDescription()));
        } else if (request.getEventType() == EventType.REPORT && !StringUtils.isNullOrEmpty(request.getObservations())) {
            newAttendanceEvent.setTitle(request.getObservations());
        }
        newAttendanceEvent.setProcedure(convertProcedure(request.getProcedure()));

        Long newEventId = attendanceEventRepo.save(newAttendanceEvent).getId();
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

    private Responsible convertResponsible(ResponsibleInfoDTO responsible) {
        logger.info("Convertendo informações do responsável :: Identificador processado -> {}", responsible.getId());
        Responsible responsibleReturn = new Responsible();
        BeanUtils.copyProperties(responsible, responsibleReturn);
        logger.info(SUCCESS_CONVERTION_MESSAGE);
        return responsibleReturn;
    }

    private Accommodation convertAccommodation(AccommodationDTO accommodation) {
        logger.info("Convertendo informações de Sala ou Leito :: Identificador processado -> {}", accommodation.getId());
        Accommodation accommodationReturn = new Accommodation();
        BeanUtils.copyProperties(accommodation, accommodationReturn);
        logger.info(SUCCESS_CONVERTION_MESSAGE);
        return accommodationReturn;
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

    private Procedure convertProcedure(ProcedureDTO procedure) {
        logger.info("Convertendo informações de procedimento");

        Procedure procedureReturn = null;
        if (procedure != null && procedure.getId() != null) {
            procedureReturn = procedureService.findById(procedure.getId());
        }
        return procedureReturn;
    }
}
