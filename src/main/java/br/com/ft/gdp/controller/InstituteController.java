package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.models.dto.InstituteInfoDTO;
import br.com.ft.gdp.service.InstituteService;
import io.swagger.annotations.Api;

/**
 * Classe VisitEventController.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@Api("Endpoint - Informações da Instituição")
@RestController
@RequestMapping(value = "/institute")
public class InstituteController {

    @Autowired()
    private InstituteService service;

    @GetMapping("/about")
    public ResponseEntity<InstituteInfoDTO> getInfo() {
        return ResponseEntity.ok(service.getInstitute());
    }
}
