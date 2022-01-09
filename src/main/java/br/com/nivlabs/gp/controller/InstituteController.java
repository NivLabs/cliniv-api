package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.CustomerInfoDTO;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.service.InstituteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe InstituteController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Tag(name = "Instituição", description = "Endpoint - Informações da Instituição")
@RestController
@RequestMapping(value = "/institute")
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    @Operation(summary = "institute-get-settings", description = "Busca as informações da Instituição")
    @GetMapping
    @PreAuthorize("hasAnyRole('INSTINTUTO_LEITURA', 'INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<InstituteDTO> getInstitute() {
        return ResponseEntity.ok(instituteService.getSettings());
    }

    @Operation(summary = "institute-upload-logo", description = "Insere a logo da Instituição")
    @PostMapping("/logo")
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> uploadCompanyLogo(@RequestBody(required = true) FileDTO file) {
        instituteService.setCompanyLogo(file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "institute-upload-customerinfo", description = "Insere a chave de acesso ao sistema")
    @PostMapping("/license")
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> uploadCustomInfoCrypto(@RequestBody(required = true) FileDTO file) {
        instituteService.checkAndActiveLicense(file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "institute-customerinfo-create-update", description = "Cria ou altera informações da instituição")
    @PostMapping
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> create(@RequestBody(required = true) CustomerInfoDTO request) {
        instituteService.createOrUpdate(request);
        return ResponseEntity.ok().build();
    }
}
