package br.com.nivlabs.cliniv.controller.attendance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.cliniv.service.evolution.EvolutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador de entrada para processos com evolução clínica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Tag(name = "Evolução do Paciente", description = "Endpoint - Evolução clínica do paciente")
@RestController
@RequestMapping("/attendance/evolution")
public class EvolutionController extends BaseController<EvolutionService> {

    @Operation(summary = "evolution-post", description = "Insere uma nova evolução do paciente na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<EvolutionInfoDTO> persist(@Validated @RequestBody(required = true) EvolutionInfoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewEvolution(request));
    }

}
