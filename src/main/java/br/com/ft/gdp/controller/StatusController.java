package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.models.dto.ServerStatusDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe StatusController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 * @since 05 Mai, 2020
 */
@Api("Endpoint - Status")
@RestController
@RequestMapping(value = "/status")
public class StatusController {

    @Autowired
    BuildProperties buildProperties;

    @ApiOperation(nickname = "status-get", value = "Bosca o status do servidor")
    @GetMapping
    public ResponseEntity<ServerStatusDTO> getSectorsGroupedBySuper() {
        ServerStatusDTO serverStatus = new ServerStatusDTO();
        if (buildProperties.getName() != null)
            serverStatus.setApplicationName(buildProperties.getName());
        if (buildProperties.getVersion() != null)
            serverStatus.setVersion(buildProperties.getVersion());

        return ResponseEntity.ok(serverStatus);
    }

}
