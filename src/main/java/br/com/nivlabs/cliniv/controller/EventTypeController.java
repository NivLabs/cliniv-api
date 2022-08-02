package br.com.nivlabs.cliniv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.models.dto.EventTypeDTO;
import br.com.nivlabs.cliniv.service.eventtype.EventTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe EventTypeController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Tag(name = "Tipo de evento", description = "Endpoint - Tipo Evento")
@RestController
@RequestMapping(value = "/event-type")
public class EventTypeController {

    @Autowired
    private EventTypeService service;

    @Operation(summary = "eventtype-get", description = "Busca uma página de tipos de eventos")
    @GetMapping
    public ResponseEntity<List<EventTypeDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "100") Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "description") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<EventTypeDTO> returnList = new ArrayList<>();
        service.searchEntityPage(pageSettings).getContent().forEach(eventType -> {
            EventTypeDTO newEventType = new EventTypeDTO();
            BeanUtils.copyProperties(eventType, newEventType);
            returnList.add(newEventType);
        });

        return ResponseEntity.ok(returnList);
    }

}
