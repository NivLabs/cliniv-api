package br.com.ft.gdp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.event.CreatedResourceEvent;
import br.com.ft.gdp.models.dto.SectorDTO;
import br.com.ft.gdp.service.SectorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe SectorController.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 Dez, 2019
 */
@Api("Endpoint - Setor")
@RestController
@RequestMapping(value = "/sector")
public class SectorController {

    @Autowired
    private SectorService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "sector-get", value = "Busca uma página de setores")
    @GetMapping
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<List<SectorDTO>> getSectorsGroupedBySuper() {
        return ResponseEntity.ok(service.getSectorsGroupedBySuper());
    }

    @ApiOperation(nickname = "sector-post", value = "Insere um novo setor na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<SectorDTO> persist(@Validated @RequestBody(required = true) SectorDTO newsector,
                                             HttpServletResponse response) {
        SectorDTO createdsector = service.persist(newsector);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdsector.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdsector);

    }

    @ApiOperation(nickname = "sector-put", value = "Atualiza um setor na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<SectorDTO> update(@PathVariable("id") Long id,
                                            @Validated @RequestBody(required = true) SectorDTO sector,
                                            HttpServletResponse response) {
        SectorDTO createdResponsible = service.update(id, sector);

        return ResponseEntity.ok().body(createdResponsible);

    }

    @ApiOperation(nickname = "sector-get-id", value = "Busca um setor baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<SectorDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
