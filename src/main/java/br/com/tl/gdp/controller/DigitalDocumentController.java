package br.com.tl.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tl.gdp.models.dto.DigitalDocumentDTO;
import br.com.tl.gdp.service.DigitalDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author viniciosarodrigues
 *
 */
@Api("Endpoint - Informações de Documentos Digitais")
@RestController
@RequestMapping(value = "/digital-document")
public class DigitalDocumentController {

    @Autowired()
    private DigitalDocumentService service;

    @ApiOperation(nickname = "digital-document-get-about", value = "Busca documento digital por identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCUMENTO_LEITURA', 'DOCUMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DigitalDocumentDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findDtoById(id));
    }
}
