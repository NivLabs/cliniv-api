package br.com.nivlabs.cliniv.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.models.dto.InstituteDTO;
import br.com.nivlabs.cliniv.models.dto.NewParameterValueDTO;
import br.com.nivlabs.cliniv.service.parameter.ParameterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe InstituteController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Tag(name = "Parâmetros", description = "Endpoint - Manipulação de parâmetros")
@RestController
@RequestMapping(value = "/parameter")
public class ParameterController extends BaseController<ParameterService> {

    @Operation(summary = "parameter-put-parameter", description = "Atualiza valor de parâmetro de aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PARAMETRO_ESCRITA', 'ADMIN')")
    public ResponseEntity<InstituteDTO> updateParameter(@PathVariable("id") Long id, @RequestBody NewParameterValueDTO newValue) {
        service.changeValueParameter(id, newValue);
        return ResponseEntity.noContent().build();
    }
}
