package br.com.nivlabs.gp.controller.healthoperator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.BaseController;
import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.gp.service.healthoperator.HealthOperatorService;
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
public class HealthOperatorController extends BaseController<HealthOperatorService> {

    @ApiOperation(nickname = "health-operator-get", value = "Busca uma página de operadoras de plano de saúde")
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<HealthOperatorDTO>> findPage(HealthOperatorFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(),
                                               Direction.valueOf(filters.getDirection()), filters.getOrderBy());
        return ResponseEntity.ok(service.getPage(filters, pageSettings));
    }

    @ApiOperation(nickname = "health-operator-get-id", value = "Busca uma operadora baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findByHealthOperatorId(id));
    }

    @ApiOperation(nickname = "health-operator-post", value = "Adiciona uma operadora de saúde")
    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> creatHealthOperator(@Validated @RequestBody(required = true) HealthOperatorInfoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));

    }

    @ApiOperation(nickname = "health-operator-put", value = "Atualiza uma operadora de saúde")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> updateHealthOperator(@PathVariable("id") Long id,
                                                                      @Validated @RequestBody(required = true) HealthOperatorInfoDTO request) {

        HealthOperatorInfoDTO responseDTO = service.update(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
