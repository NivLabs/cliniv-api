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
import br.com.ft.gdp.models.domain.EventType;
import br.com.ft.gdp.models.dto.EventTypeDTO;
import br.com.ft.gdp.service.EventTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe EventTypeController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Api("Endpoint - Tipo Evento")
@RestController
@RequestMapping(value = "/eventtype")
public class EventTypeController {

    @Autowired
    private EventTypeService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "eventtype-get", value = "Busca uma página de tipos de eventos")
    @GetMapping
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<Page<EventType>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                    @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                    @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(service.searchEntityPage(pageSettings));
    }

    @ApiOperation(nickname = "responsible-post", value = "Insere um novo tipo de evento na aplicação")
    @PostMapping
    public ResponseEntity<EventTypeDTO> persist(@Validated @RequestBody(required = true) EventTypeDTO eventType,
                                                HttpServletResponse response) {
        EventType createdEventType = service.persist(eventType.getEventTypeDomainFromDTO());

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdEventType.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventType.getEventTypeDTOFromDomain());

    }

    @ApiOperation(nickname = "responsible-put", value = "Atualiza um tipo de evento na aplicação")
    @PutMapping("/{id}")
    public ResponseEntity<EventTypeDTO> update(@PathVariable("id") Long id,
                                               @Validated @RequestBody(required = true) EventTypeDTO eventType,
                                               HttpServletResponse response) {
        EventType createdResponsible = service.update(id, eventType.getEventTypeDomainFromDTO());

        return ResponseEntity.ok().body(createdResponsible.getEventTypeDTOFromDomain());

    }

    @ApiOperation(nickname = "eventtype-get-id", value = "Busca um tipo de evento baseado no identificador")
    @GetMapping("/{id}")
    public ResponseEntity<EventType> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
