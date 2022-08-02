package br.com.nivlabs.cliniv.controller;

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

import br.com.nivlabs.cliniv.controller.filters.ResponsibleFilters;
import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.models.dto.ResponsibleDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe ResponsibleController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Tag(name = "Profissionais e Responsáveis", description = "Endpoint - Responsáveis")
@RestController
@RequestMapping(value = "/responsible")
public class ResponsibleController extends BaseController<ResponsibleService> {

    @Operation(summary = "responsible-get", description = "Busca uma página de responsáveis")
    @GetMapping
    @PreAuthorize("hasAnyRole('PROFISSIONAL_LEITURA', 'PROFISSIONAL_ESCRITA',  'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Page<ResponsibleDTO>> findPage(ResponsibleFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.searchEntityPage(filters, pageSettings));
    }

    @Operation(summary = "responsible-post", description = "Insere um novo responsável na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('PROFISSIONAL_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> persist(@Validated @RequestBody(required = true) ResponsibleInfoDTO responsible) {
        ResponsibleInfoDTO createdResponsible = service.create(responsible);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponsible);

    }

    @Operation(summary = "responsible-put", description = "Atualiza um responsável na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> update(@PathVariable("id") Long id,
                                                     @Validated @RequestBody(required = true) ResponsibleInfoDTO responsible) {
        ResponsibleInfoDTO createdResponsible = service.update(id, responsible);

        return ResponseEntity.ok().body(createdResponsible);

    }

    @Operation(summary = "responsible-get-id", description = "Busca um responsável baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL_LEITURA', 'PROFISSIONAL_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "responsible-get-by-document", description = "Busca um profissional pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL_LEITURA', 'PROFISSIONAL_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ResponsibleInfoDTO> findByDocument(@PathVariable("documentType") DocumentType documentType,
                                                             @PathVariable("document") String document) {
        return ResponseEntity.ok(service.findByCpf(document));
    }

}
