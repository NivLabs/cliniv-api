package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.ProcedureOrEventFilters;
import br.com.nivlabs.gp.models.dto.ProcedureOrEventDTO;
import br.com.nivlabs.gp.service.ProcedureOrEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe AttendanceController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Api("Endpoint - Procedimento ou Evento")
@RestController
@RequestMapping(value = "/procedure")
public class ProcedureOrEventController {

    @Autowired
    private ProcedureOrEventService service;

    @ApiOperation(nickname = "procedure-get", value = "Busca uma página de procedimentos")
    @GetMapping
    public ResponseEntity<Page<ProcedureOrEventDTO>> findPage(ProcedureOrEventFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.getResumedPage(filters, pageSettings));
    }

}
