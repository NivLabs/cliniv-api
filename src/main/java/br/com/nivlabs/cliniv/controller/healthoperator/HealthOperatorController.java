package br.com.nivlabs.cliniv.controller.healthoperator;

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

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.cliniv.models.dto.HealthOperatorDTO;
import br.com.nivlabs.cliniv.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.cliniv.service.healthoperator.HealthOperatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe HealthOperatorController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 4 de agosto de 2020
 */
@Tag(name = "Operadora de Saúde", description = "Endpoint - Operações com operadoras de saúde")
@RestController
@RequestMapping(value = "/health-operator")
public class HealthOperatorController extends BaseController<HealthOperatorService> {

    @Operation(summary = "health-operator-get", description = "Busca uma página de operadoras de plano de saúde")
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<HealthOperatorDTO>> findPage(HealthOperatorFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(),
                                               Direction.valueOf(filters.getDirection()), filters.getOrderBy());
        return ResponseEntity.ok(service.getPage(filters, pageSettings));
    }

    @Operation(summary = "health-operator-get-id", description = "Busca uma operadora baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findByHealthOperatorId(id));
    }

    @Operation(summary = "health-operator-post", description = "Adiciona uma operadora de saúde")
    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> creatHealthOperator(@Validated @RequestBody(required = true) HealthOperatorInfoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));

    }

    @Operation(summary = "health-operator-put", description = "Atualiza uma operadora de saúde")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthOperatorInfoDTO> updateHealthOperator(@PathVariable("id") Long id,
                                                                      @Validated @RequestBody(required = true) HealthOperatorInfoDTO request) {

        HealthOperatorInfoDTO responseDTO = service.update(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
