package br.com.nivlabs.gp.controller.attendance;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.service.AttendanceEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe AttendanceEventController.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@Api("Endpoint - Eventos do atendimento")
@RestController
@RequestMapping(value = "/attendance/event")
public class AttendanceEventController {

    @Autowired
    private AttendanceEventService service;

    @ApiOperation(nickname = "attendance-event-post", value = "Insere um novo evento de atendimento")
    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<Void> persist(
                                        @Validated @RequestBody(required = true) NewAttendanceEventDTO newAttendanceEvent,
                                        HttpServletResponse response) {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        service.persistNewAttendanceEvent(newAttendanceEvent, userFromSession);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
