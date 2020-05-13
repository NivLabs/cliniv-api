package br.com.ft.gdp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.event.CreatedResourceEvent;
import br.com.ft.gdp.models.domain.AttendanceEvent;
import br.com.ft.gdp.service.VisitEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe VisitEventController.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@Api("Endpoint - Eventos de Visita")
@RestController
@RequestMapping(value = "/visit-event")
public class VisitEventController {

    @Autowired
    private VisitEventService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "visit-event-get", value = "Busca uma página de eventos de visita")
    @GetMapping
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<Page<AttendanceEvent>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                     @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                     @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(service.searchEntityPage(pageSettings));
    }

    @ApiOperation(nickname = "visit-event-post", value = "Insere um novo evento de visita")
    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<AttendanceEvent> persist(@Validated @RequestBody(required = true) AttendanceEvent newVisitEvent,
                                              HttpServletResponse response) {
        AttendanceEvent createdVisitEvent = service.persist(newVisitEvent);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdVisitEvent.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisitEvent);

    }

    @ApiOperation(nickname = "visit-event-put", value = "Atualiza um evento de visita na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<AttendanceEvent> update(@PathVariable("id") Long id,
                                             @Validated @RequestBody(required = true) AttendanceEvent visitEvent, HttpServletResponse response) {
        AttendanceEvent createdVisitEvent = service.update(id, visitEvent);

        return ResponseEntity.ok().body(createdVisitEvent);

    }

    @ApiOperation(nickname = "visit-event-get-id", value = "Busca um evento de visita baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<AttendanceEvent> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
