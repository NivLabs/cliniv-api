package br.com.nivlabs.cliniv.controller;

import javax.servlet.http.HttpServletResponse;

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

import br.com.nivlabs.cliniv.controller.filters.ProcedureFilters;
import br.com.nivlabs.cliniv.models.dto.ProcedureDTO;
import br.com.nivlabs.cliniv.models.dto.ProcedureInfoDTO;
import br.com.nivlabs.cliniv.service.procedure.ProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe AttendanceController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Tag(name = "Procedimentos e Eventos", description = "Endpoint - Procedimento ou Evento")
@RestController
@RequestMapping(value = "/procedure")
public class ProcedureController extends BaseController<ProcedureService> {

    @Operation(summary = "procedure-get", description = "Busca uma página de procedimentos")
    @GetMapping
    public ResponseEntity<Page<ProcedureDTO>> findPage(ProcedureFilters filters) {
        return ResponseEntity.ok(service.getResumedPage(filters));
    }

    @Operation(summary = "procedure-get-id", description = "Busca um procedimento à partir do identificador")
    @GetMapping("{id}")
    public ResponseEntity<ProcedureInfoDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "procedure-post", description = "Cria um novo procedimento no sistema")
    @PostMapping
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ProcedureInfoDTO> persist(@Validated @RequestBody(required = true) ProcedureInfoDTO request,
                                                    HttpServletResponse response) {
        ProcedureInfoDTO createdProcedure = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProcedure);
    }

    @Operation(summary = "procedure-put", description = "Atualiza um procedimento existente no sistema")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ProcedureInfoDTO> update(@PathVariable("id") Long id,
                                                   @Validated @RequestBody(required = true) ProcedureInfoDTO request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @Operation(summary = "procedure-delete-id", description = "Deleta um procedimento pelo identificador")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
