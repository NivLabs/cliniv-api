package br.com.nivlabs.gp.controller.healthoperator;

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

import br.com.nivlabs.gp.controller.BaseController;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.service.healthplan.HealthPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint - HealthPlan")
@RestController
@RequestMapping(value = "/health-operator/health-plan")
public class HealthPlanController extends BaseController<HealthPlanService> {

    @ApiOperation(nickname = "health-plan-get-id", value = "Busca um plano de saúde baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> findHealthPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findHealthPlanById(id));
    }

    @ApiOperation(nickname = "health-plan-get-ansCode", value = "Busca um plano de saúde baseada no código da ANS")
    @GetMapping("/ansCode/{ansCode}")
    @PreAuthorize("hasAnyRole('OPERADORA_LEITURA', 'OPERADORA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> findHealthPlanByAnsCode(@PathVariable("ansCode") Long ansCode) {
        return ResponseEntity.ok(service.findHealthPlanByAnsCode(ansCode));
    }

    @ApiOperation(nickname = "health-plan-post", value = "Adiciona um plano de saúde")
    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> updateHealthPlan(@Validated @RequestBody(required = true) HealthPlanDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @ApiOperation(nickname = "health-plan-delet", value = "Deleta uma operadora de saúde")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> deleteHealthPlan(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(nickname = "health-plan-put", value = "Atualiza um plano de saúde")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADORA_ESCRITA', 'ADMIN')")
    public ResponseEntity<HealthPlanDTO> updateHealthPlan(@PathVariable("id") Long id,
                                                          @Validated @RequestBody(required = true) HealthPlanDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(id, request));
    }

}
