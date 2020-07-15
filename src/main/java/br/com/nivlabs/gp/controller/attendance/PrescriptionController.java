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
import br.com.nivlabs.gp.models.dto.PrescriptionInfoDTO;
import br.com.nivlabs.gp.service.PrescriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controlador de entrada para processos com precrição médica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Api(value = "Endpoint - Prescrição do paciente")
@RestController
@RequestMapping("/attendance/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService service;

    @ApiOperation(nickname = "prescription-post", value = "Insere uma nova prescrição do paciente no atendimento")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<PrescriptionInfoDTO> persist(@Validated @RequestBody(required = true) PrescriptionInfoDTO request,
                                                       HttpServletResponse response) {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPrescription(request, userFromSession.getUsername()));
    }

}
