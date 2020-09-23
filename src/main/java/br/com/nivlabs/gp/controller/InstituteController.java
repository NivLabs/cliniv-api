package br.com.nivlabs.gp.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
    public ResponseEntity<Void> uploadCompanyLogo(@RequestBody(required = true) FileDTO file) {
        instituteService.setCompanyLogo(file);
        return ResponseEntity.ok().build();
    }
    
    @ApiOperation(nickname = "institute-upload-custominfo", value = "Insere a chave de acesso ao sistema")
    @PostMapping
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> uploadCustomInfoCrypto(@RequestBody(required = true) FileDTO file) {
        try {
			instituteService.setCustomerInfoCrypto(file);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
		} 
        return ResponseEntity.ok().build();
    }
    
    
    @ApiOperation(nickname = "institute-post", value = "Insere um novo Instituto")
    @PostMapping
    @PreAuthorize("hasAnyRole('INSTITUTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<InstituteDTO> persist(@Validated @RequestBody(required = true) InstituteDTO newInstitute,
                                                  HttpServletResponse response) {
    	InstituteDTO createdInstitute = instituteService.persist(newInstitute);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstitute);

    }
}
