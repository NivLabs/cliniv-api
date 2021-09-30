package br.com.nivlabs.gp.controller.attendance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.BaseController;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.service.evolution.EvolutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controlador de entrada para processos com evolução clínica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Api(value = "Endpoint - Evolução clínica do paciente")
@RestController
@RequestMapping("/attendance/evolution")
public class EvolutionController extends BaseController<EvolutionService> {

    @ApiOperation(nickname = "evolution-post", value = "Insere uma nova evolução do paciente na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<EvolutionInfoDTO> persist(@Validated @RequestBody(required = true) EvolutionInfoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewEvolution(request));
    }

}
