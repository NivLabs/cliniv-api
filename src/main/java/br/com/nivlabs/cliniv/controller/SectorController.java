package br.com.nivlabs.cliniv.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.filters.SectorFilters;
import br.com.nivlabs.cliniv.event.CreatedResourceEvent;
import br.com.nivlabs.cliniv.models.dto.AccommodationDTO;
import br.com.nivlabs.cliniv.models.dto.SectorDTO;
import br.com.nivlabs.cliniv.models.dto.SectorInfoDTO;
import br.com.nivlabs.cliniv.service.sector.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe SectorController.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 Dez, 2019
 */
@Tag(name = "Setor", description = "Endpoint - Setor")
@RestController
@RequestMapping(value = "/sector")
public class SectorController extends BaseController<SectorService> {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Operation(summary = "sector-get", description = "Busca uma página de setores")
    @GetMapping
    @PreAuthorize("hasAnyRole('SETOR_LEITURA', 'SETOR_ESCRITA', 'EVENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Page<SectorDTO>> getPageSectors(SectorFilters filters) {
        return ResponseEntity.ok(service.getPage(filters));
    }

    @Operation(summary = "sector-post", description = "Insere um novo setor na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('SETOR_ESCRITA', 'ADMIN')")
    public ResponseEntity<SectorInfoDTO> persist(@Validated @RequestBody(required = true) SectorInfoDTO newsector,
                                                 HttpServletResponse response) {
        SectorInfoDTO createdsector = service.persist(newsector);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdsector.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdsector);

    }

    @Operation(summary = "sector-put", description = "Atualiza um setor na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SETOR_ESCRITA', 'ADMIN')")
    public ResponseEntity<SectorInfoDTO> update(@PathVariable("id") Long id,
                                                @Validated @RequestBody(required = true) SectorInfoDTO sector) {
        return ResponseEntity.ok().body(service.update(id, sector));
    }

    @Operation(summary = "room-or-bet-put", description = "Atualiza uma sala (ambulatório) ou leito na aplicação")
    @PutMapping("/room-or-bed/{id}")
    @PreAuthorize("hasAnyRole('SETOR_ESCRITA', 'ADMIN')")
    public ResponseEntity<AccommodationDTO> updateRoomOrBed(@PathVariable("id") Long id,
                                                            @Validated @RequestBody(required = true) AccommodationDTO request) {
        return ResponseEntity.ok().body(service.updateAccommodation(id, request));
    }

    @Operation(summary = "room-or-bet-post", description = "Cria uma sala (ambulatório) ou leito na aplicação")
    @PostMapping("/room-or-bed")
    @PreAuthorize("hasAnyRole('SETOR_ESCRITA', 'ADMIN')")
    public ResponseEntity<AccommodationDTO> persist(@Validated @RequestBody(required = true) AccommodationDTO request,
                                                    HttpServletResponse response) {
        AccommodationDTO createdsector = service.persist(request);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdsector.getId()));

        return ResponseEntity.ok().body(createdsector);
    }

    @Operation(summary = "room-or-bet-delete", description = "Deleta uma sala (ambulatório) ou leito na aplicação")
    @DeleteMapping("/room-or-bed/{id}")
    @PreAuthorize("hasAnyRole('SETOR_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> deleteRoomOrBed(@PathVariable("id") Long id) {
        service.deletetAccomodationById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "sector-get-id", description = "Busca um setor baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SETOR_LEITURA', 'SETOR_ESCRITA', 'EVENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<SectorInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findInfoById(id));
    }
}
