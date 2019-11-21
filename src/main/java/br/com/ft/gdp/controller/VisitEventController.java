package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.service.VisitEventService;
import io.swagger.annotations.Api;

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

}
