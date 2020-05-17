package br.com.tl.gdp.controller;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.tl.gdp.controller.filters.ResponsibleFilters;
import br.com.tl.gdp.event.CreatedResourceEvent;
import br.com.tl.gdp.models.dto.ResponsibleDTO;
import br.com.tl.gdp.models.dto.ResponsibleInfoDTO;
import br.com.tl.gdp.models.enums.DocumentType;
import br.com.tl.gdp.service.ResponsibleService;
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
    @PreAuthorize("hasAnyRole('PROFISSIONAL_LEITURA', 'PROFISSIONAL_ESCRITA',  'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Page<ResponsibleDTO>> findPage(ResponsibleFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.searchEntityPage(filters, pageSettings));
    }

    @ApiOperation(nickname = "responsible-post", value = "Insere um novo responsável na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('PROFISSIONAL_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleDTO> persist(@Validated @RequestBody(required = true) ResponsibleInfoDTO responsible,
                                                  HttpServletResponse response) {
        ResponsibleDTO createdResponsible = service.persistDTO(responsible);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdResponsible.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponsible);

    }

    @ApiOperation(nickname = "responsible-put", value = "Atualiza um responsável na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> update(@PathVariable("id") Long id,
                                                     @Validated @RequestBody(required = true) ResponsibleInfoDTO responsible,
                                                     HttpServletResponse response) {
        ResponsibleInfoDTO createdResponsible = service.update(id, responsible);

        return ResponseEntity.ok().body(createdResponsible);

    }

    @ApiOperation(nickname = "responsible-get-id", value = "Busca um responsável baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL_LEITURA', 'PROFISSIONAL_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @ApiOperation(nickname = "responsible-get-by-document", value = "Busca um profissional pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL_LEITURA', 'PROFISSIONAL_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> findByDocument(@PathVariable("documentType") DocumentType documentType,
                                                             @PathVariable("document") String document) {
        return ResponseEntity.ok(service.findByCpf(document));
    }

}
