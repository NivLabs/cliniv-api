package br.com.nivlabs.gp.service.attendance.business;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.CloseAttandenceDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceRepository;
import br.com.nivlabs.gp.service.AttendanceEventService;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.ResponsibleService;
import br.com.nivlabs.gp.service.UserService;
import br.com.nivlabs.gp.util.SecurityContextUtil;

/**
 * 
 * Camada de negócio para processo de encerramento de atendimento
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class CloseAttendanceBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private AttendanceRepository attendanceRepo;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttendanceEventService attendanceEventService;

    /**
     * Realiza alta de paciente com atendimento ativo
     *
     * @param id Identificador único do atendimento
     * @param request Objeto de requisição de fechamento de atendimento
     */
    public void closeAttendance(Long id, CloseAttandenceDTO request) {
        logger.info("Iniciando processo de encerramento de atendimento,\nVerificando situação do atendimento solicitado...");
        Attendance attendanceFromDb = attendanceRepo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                "Não é possível encerrar um atendimento inexistente"));
        if (attendanceFromDb.getExitDateTime() != null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Este atendimento já foi encerrado");
        }
        NewAttendanceEventDTO newAttendanceEvent = createAttendanceFromCloseRequest(attendanceFromDb, request);
        attendanceFromDb.setExitDateTime(newAttendanceEvent.getEventDateTime());
        attendanceRepo.save(attendanceFromDb);
    }

    /**
     * Cria um evento de encerramento de atendimento (Alta)
     *
     * @param attendanceEntity Entidade relacional de atendimento
     * @param request Objeto de requisição de alta
     * @return Novo evento de atendimento
     */
    private NewAttendanceEventDTO createAttendanceFromCloseRequest(Attendance attendanceEntity, CloseAttandenceDTO request) {
        logger.info("Criando evento de atendimento para alta de paciente");

        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setAttendanceId(attendanceEntity.getId());
        event.setEventDateTime(LocalDateTime.now());
        event.setEventType(request.getEventType());
        event.setAccommodation(new AccommodationDTO(attendanceEntity.getCurrentAccommodation().getId()));
        event.setResponsible(getResponsibleFromUserSession());
        event.setObservations(request.getObservations());

        attendanceEventService.persistNewAttendanceEvent(event, null);
        logger.info("Evento de Atendimento para alta de paciente criado com sucesso!");

        return event;
    }

    /**
     * Busca o usuário logado na sessão
     *
     * @return
     */
    private ResponsibleInfoDTO getResponsibleFromUserSession() {
        logger.info("Buscando usuário da sessão...");
        String userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        UserInfoDTO logedUser = userService.findByUserName(userName);

        logger.info("Iniciando busca de responsável pelo usuário da requisição...");
        ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(logedUser.getDocument().getValue());
        if (responsibleInformations.getId() == null)
            throw new HttpException(HttpStatus.FORBIDDEN, "Sem presmissão! Você não tem um profissional vinculado ao seu usuário.");
        logger.info("Profissional encontrado :: {}", responsibleInformations.getFullName());
        return responsibleInformations;
    }
}
