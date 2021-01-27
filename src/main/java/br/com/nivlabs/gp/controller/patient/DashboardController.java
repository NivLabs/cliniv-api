package br.com.nivlabs.gp.controller.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.DashboardInfoDTO;
import br.com.nivlabs.gp.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint - Paciente")
@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @ApiOperation(nickname = "dashboard-get", value = "Busca informações do dashboard")
    @GetMapping
    public ResponseEntity<DashboardInfoDTO> findInfos() {
        return ResponseEntity.ok(service.getInfo());
    }

}
