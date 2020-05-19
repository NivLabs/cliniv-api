package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.service.ParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe InstituteController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Api("Endpoint - Manipulação de parâmetros")
@RestController
@RequestMapping(value = "/parameter")
public class ParameterController {

    @Autowired
    private ParameterService service;

    @ApiOperation(nickname = "parameter-put-parameter", value = "Atualiza valor de parâmetro de aplicação")
    @PutMapping("/{id}/{newValue}")
    @PreAuthorize("hasAnyRole('PARAMETRO_ESCRITA', 'ADMIN')")
    public ResponseEntity<InstituteDTO> getInstitute(@PathVariable("id") Long id, @PathVariable("newValue") String newValue) {
        service.changeValueParameter(id, newValue);
        return ResponseEntity.noContent().build();
    }
}
