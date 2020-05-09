
/**
 * 
 */
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
import br.com.ft.gdp.models.domain.Anamnesis;
import br.com.ft.gdp.models.dto.AnamnesisDTO;
import br.com.ft.gdp.service.AnamnesisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AnamneseController.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 14 de set de 2019
 * 
 */

@Api(value = "Endpoint - Anamnese")
@RestController
@RequestMapping("/anamnese")
public class AnamnesisController {

    @Autowired
    private AnamnesisService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "anamnese-get", value = "Busca uma página de Anamnesis")
    @GetMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<AnamnesisDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(service.searchDTOPage(pageSettings));
    }

    @ApiOperation(nickname = "anamnese-post", value = "Insere uma nova anamnese na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<AnamnesisDTO> persist(@Validated @RequestBody(required = true) AnamnesisDTO anamneseDto,
                                                HttpServletResponse response) {
        Anamnesis createdAnamnese = service.persist(anamneseDto.getAnamnesesDomainFromDTO());

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdAnamnese.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnamnese.getAnamneseDTOFromDomain());

    }

    @ApiOperation(nickname = "anamnese-put", value = "Atualiza uma anamnese na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<AnamnesisDTO> update(@PathVariable("id") Long id,
                                               @Validated @RequestBody(required = true) AnamnesisDTO anamneseDTO,
                                               HttpServletResponse response) {
        Anamnesis createdAnamnese = service.update(id, anamneseDTO.getAnamnesesDomainFromDTO());

        return ResponseEntity.ok().body(createdAnamnese.getAnamneseDTOFromDomain());

    }

    @ApiOperation(nickname = "anamnese-get-id", value = "Busca uma anamnese baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<AnamnesisDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id).getAnamneseDTOFromDomain());
    }

}
