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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.event.CreatedResourceEvent;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.dto.ResponsibleDTO;
import br.com.ft.gdp.service.ResponsibleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe ResponsibleController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Api("Endpoint - Responsáveis")
@RestController
@RequestMapping(value = "/responsible")
public class ResponsibleController {

    @Autowired
    private ResponsibleService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "responsible-get", value = "Busca uma página de responsáveis")
    @GetMapping
    public ResponseEntity<Page<Responsible>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                      @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(service.searchEntityPage(pageSettings));
    }

    @ApiOperation(nickname = "responsible-post", value = "Insere um novo responsável na aplicação")
    @PostMapping
    public ResponseEntity<ResponsibleDTO> persist(@Validated @RequestBody(required = true) ResponsibleDTO responsible,
                                                  HttpServletResponse response) {
        Responsible createdResponsible = service.persist(responsible.getResponsibleDomainFromDTO());

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdResponsible.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponsible.getResponsibleDTOFromDomain());

    }

    @ApiOperation(nickname = "responsible-get-id", value = "Busca um responsável baseano no identificador")
    @GetMapping("/{id}")
    public ResponseEntity<Responsible> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @ApiOperation(nickname = "responsible-get-cpf", value = "Busca um responsável baseano no cpf")
    @GetMapping("/{cpf}/cpf")
    public ResponseEntity<Responsible> findByCpf(@PathVariable("cpf") String cpf) {
        return ResponseEntity.ok(service.findByCpf(cpf));
    }

}
