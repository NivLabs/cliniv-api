package br.com.nivlabs.cliniv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.models.dto.ServerStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe StatusController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 * @since 05 Mai, 2020
 */
@Tag(name = "Status", description = "Endpoint - Status")
@RestController
@RequestMapping(value = "/status")
public class StatusController {

    @Autowired
    BuildProperties buildProperties;

    @Operation(summary = "status-get", description = "Busca o status do servidor")
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
