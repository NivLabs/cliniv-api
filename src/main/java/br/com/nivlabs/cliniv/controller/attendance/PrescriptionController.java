package br.com.nivlabs.cliniv.controller.attendance;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.models.dto.PrescriptionInfoDTO;
import br.com.nivlabs.cliniv.service.attendance.prescription.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador de entrada para processos com precrição médica do paciente
 *
 * @author viniciosarodrigues
 * @since 10-10-2021
 *
 */
@Tag(name = "Precrição do paciente", description = "Endpoint - Prescrição do paciente")
@RestController
@RequestMapping("/attendance/prescription")
public class PrescriptionController extends BaseController<PrescriptionService> {

    @Operation(summary = "prescription-post", description = "Insere uma nova prescrição do paciente no atendimento")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> persist(@Validated @RequestBody(required = true) PrescriptionInfoDTO request,
                                        HttpServletResponse response) {
        service.createPrescription(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
