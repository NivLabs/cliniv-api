package br.com.nivlabs.gp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.DashboardCardsInformationDTO;
import br.com.nivlabs.gp.service.dashboard.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint - Dashboard")
@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController extends BaseController<DashboardService> {

    @ApiOperation(nickname = "dashboard-get", value = "Busca informações do dashboard")
    @GetMapping
    public ResponseEntity<DashboardCardsInformationDTO> findInfos() {
        return ResponseEntity.ok(service.getCardsInformations());
    }

}
