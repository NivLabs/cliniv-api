package br.com.nivlabs.gp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.service.digitaldocument.DigitalDocumentService;
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
public class DigitalDocumentController extends BaseController<DigitalDocumentService> {

    @ApiOperation(nickname = "digital-document-get-about", value = "Busca documento digital por identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCUMENTO_LEITURA', 'DOCUMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DigitalDocumentDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
