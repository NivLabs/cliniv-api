package br.com.nivlabs.gp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiOperation(nickname = "institute-get-settings", value = "Busca as informações do estabelecimento")
    @GetMapping
    @PreAuthorize("hasAnyRole('INSTINTUTO_LEITURA', 'INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<InstituteDTO> getInstitute() {
        return ResponseEntity.ok(instituteService.getSettings());
    }
    
    @ApiOperation(nickname = "institute-upload-logo", value = "Insere a logo do estabelecimento")
    @PostMapping()
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public void uploadCompanyLogo(MultipartFile logo) throws Throwable {
    	System.gc();
		byte[] bytes = logo.getBytes();

		File file = new File(logo.getOriginalFilename());
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
		stream.write(bytes);
		stream.close();
		FileInputStream fis = new FileInputStream(file);
		instituteService.setCompanyLogo(fis);
    }
}
