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
import br.com.nivlabs.gp.repository.AttendanceRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

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
    private CreateAttendanceEventBusinessHandler createAttendanceEventBusinessHandler;

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
        event.setObservations(request.getObservations());

        createAttendanceEventBusinessHandler.create(event);
        logger.info("Evento de Atendimento para alta de paciente criado com sucesso!");

        return event;
    }
}
