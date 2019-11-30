package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.models.dto.InstituteInfoDTO;
import br.com.ft.gdp.service.InstituteService;
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

    @Autowired()
    private InstituteService service;

    @ApiOperation(nickname = "institute-get-about", value = "Busca as informações intitucionais do estabelecimento")
    @GetMapping("/about")
    public ResponseEntity<InstituteInfoDTO> getInfo() {
        return ResponseEntity.ok(service.getInstitute());
    }
}
