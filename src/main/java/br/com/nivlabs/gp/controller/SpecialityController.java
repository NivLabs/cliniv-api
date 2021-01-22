package br.com.nivlabs.gp.controller;

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

import br.com.nivlabs.gp.controller.filters.SpecialityFilter;
import br.com.nivlabs.gp.event.CreatedResourceEvent;
import br.com.nivlabs.gp.models.dto.SpecialityDTO;
import br.com.nivlabs.gp.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.gp.service.SpecialityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe SpecialityController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de jan de 2020
 */
@Api("Endpoint - Especialidade")
@RestController
@RequestMapping(value = "/speciality")
public class SpecialityController {

    @Autowired
    private SpecialityService specService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "speciality-get-page", value = "Busca uma página de especialidades")
    @GetMapping
    @PreAuthorize("hasAnyRole('ESPECIALIDADE_ESCRITA', 'ESPECIALIDADE_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<SpecialityDTO>> findList(SpecialityFilter filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(specService.searchEntityPage(filters, pageSettings));
    }

    @ApiOperation(nickname = "speciality-get-id", value = "Busca informações detalhadas de uma especialidade")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ESPECIALIDADE_ESCRITA', 'ESPECIALIDADE_ESCRITA', 'ADMIN')")
    public ResponseEntity<SpecialityInfoDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(specService.findById(id));
    }

    @ApiOperation(nickname = "speciality-post", value = "Insere uma nova especialidade na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('ESPECIALIDADE_ESCRITA', 'ADMIN')")
    public ResponseEntity<SpecialityInfoDTO> persist(@Validated @RequestBody(required = true) SpecialityInfoDTO request,
                                                     HttpServletResponse response) {
        SpecialityInfoDTO createdObj = specService.persist(request);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdObj.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdObj);

    }

    @ApiOperation(nickname = "speciality-put", value = "Atualiza um uma especialidade na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ESPECIALIDADE_ESCRITA', 'ADMIN')")
    public ResponseEntity<SpecialityInfoDTO> update(@PathVariable("id") Long id,
                                                    @Validated @RequestBody(required = true) SpecialityInfoDTO request,
                                                    HttpServletResponse response) {
        SpecialityInfoDTO createdResponsible = specService.update(id, request);

        return ResponseEntity.ok().body(createdResponsible);

    }

}
