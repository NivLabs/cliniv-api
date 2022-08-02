package br.com.nivlabs.cliniv.controller.healthoperator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.models.dto.HealthPlanDTO;
import br.com.nivlabs.cliniv.service.healthplan.HealthPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Planos de Saúde", description = "Endpoint - Operações com planos de saúde")
@RestController
@RequestMapping(value = "/health-operator/health-plan")
public class HealthPlanController extends BaseController<HealthPlanService> {

    @Operation(summary = "health-plan-get-id", description = "Busca um plano de saúde baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> findHealthPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findHealthPlanById(id));
    }

    @Operation(summary = "health-plan-get-ansCode", description = "Busca um plano de saúde baseada no código da ANS")
    @GetMapping("/ansCode/{ansCode}")
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> findHealthPlanByAnsCode(@PathVariable("ansCode") Long ansCode) {
        return ResponseEntity.ok(service.findHealthPlanByAnsCode(ansCode));
    }

    @Operation(summary = "health-plan-post", description = "Adiciona um plano de saúde")
    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> updateHealthPlan(@Validated @RequestBody(required = true) HealthPlanDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "health-plan-delet", description = "Deleta uma operadora de saúde")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> deleteHealthPlan(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "health-plan-put", description = "Atualiza um plano de saúde")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> updateHealthPlan(@PathVariable("id") Long id,
                                                          @Validated @RequestBody(required = true) HealthPlanDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(id, request));
    }

}
