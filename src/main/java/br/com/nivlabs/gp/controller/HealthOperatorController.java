package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.gp.service.HealthOperatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe HealthOperatorController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 4 de agosto de 2020
 */
@Api("Endpoint - HealthOperator")
@RestController
@RequestMapping(value = "/health-operator")
public class HealthOperatorController {

    @Autowired
    private HealthOperatorService service;

    @ApiOperation(nickname = "health-operator-get", value = "Busca uma página de operadoras de plano de saúde")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_OPERADORA_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<HealthOperatorDTO>> findPage(HealthOperatorFilters filters) {
		Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(),
				Direction.valueOf(filters.getDirection()), filters.getOrderBy());
        return ResponseEntity.ok(service.getListOfHealthOperator(filters, pageSettings));
    }

    @ApiOperation(nickname = "health-operator-get-id", value = "Busca uma operadora baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_OPERADORA_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findByHealthOperatorId(id));
    }
   
}
