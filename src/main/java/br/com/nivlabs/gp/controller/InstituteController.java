package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.service.InstituteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe InstituteController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Api("Endpoint - Informações da Instituição")
@RestController
@RequestMapping(value = "/institute")
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    @ApiOperation(nickname = "institute-get-settings", value = "Busca as informações da Instituição")
    @GetMapping
    @PreAuthorize("hasAnyRole('INSTINTUTO_LEITURA', 'INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<InstituteDTO> getInstitute() {
        return ResponseEntity.ok(instituteService.getSettings());
    }

    @ApiOperation(nickname = "institute-upload-logo", value = "Insere a logo da Instituição")
    @PostMapping
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public void uploadCompanyLogo(FileDTO file) {
        instituteService.setCompanyLogo(file);
    }
}
