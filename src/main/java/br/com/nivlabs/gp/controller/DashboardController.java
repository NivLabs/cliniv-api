package br.com.nivlabs.gp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.DashboardCardsInformationDTO;
import br.com.nivlabs.gp.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Dashboard", description = "Endpoint - Dashboard")
@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController extends BaseController<DashboardService> {

    @Operation(summary = "dashboard-get", description = "Busca informações do dashboard")
    @GetMapping
    public ResponseEntity<DashboardCardsInformationDTO> findInfos() {
        return ResponseEntity.ok(service.getCardsInformations());
    }

}
