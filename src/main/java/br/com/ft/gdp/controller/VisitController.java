package br.com.ft.gdp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.event.CreatedResourceEvent;
import br.com.ft.gdp.models.domain.Visit;
import br.com.ft.gdp.models.dto.NewVisitDTO;
import br.com.ft.gdp.models.dto.VisitDTO;
import br.com.ft.gdp.service.VisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe VisitController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Api("Endpoint - Visita")
@RestController
@RequestMapping(value = "/visit")
public class VisitController {

    @Autowired
    private VisitService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "visit-post", value = "Insere uma nova visita na aplicação")
    @PostMapping
    public ResponseEntity<VisitDTO> persist(@Validated @RequestBody(required = true) NewVisitDTO visit,
                                            HttpServletResponse response) {
        Visit createdVisit = service.persistNewVisit(visit);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdVisit.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }

    @ApiOperation(nickname = "visit-put-exit", value = "Atualiza hora de saída da visita na aplicação")
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateVisitToEnd(@PathVariable("id") Long id) {
        service.closeVisit(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(nickname = "visit-get-id", value = "Busca uma visita baseado no identificador")
    @GetMapping("/{id}")
    public ResponseEntity<Visit> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
