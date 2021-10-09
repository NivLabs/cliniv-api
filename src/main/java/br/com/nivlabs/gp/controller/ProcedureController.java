package br.com.nivlabs.gp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import br.com.nivlabs.gp.controller.filters.ProcedureFilters;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.dto.ProcedureInfoDTO;
import br.com.nivlabs.gp.service.procedure.ProcedureService;
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
public class ProcedureController extends BaseController<ProcedureService> {

    @ApiOperation(nickname = "procedure-get", value = "Busca uma página de procedimentos")
    @GetMapping
    public ResponseEntity<Page<ProcedureDTO>> findPage(ProcedureFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(),
                                               Direction.valueOf(filters.getDirection()), filters.getOrderBy());
        return ResponseEntity.ok(service.getResumedPage(filters, pageSettings));
    }

    @ApiOperation(nickname = "procedure-get-id", value = "Busca um procedimento à partir do identificador")
    @GetMapping("{id}")
    public ResponseEntity<ProcedureInfoDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findDTOById(id));
    }

    @ApiOperation(nickname = "procedure-post", value = "Cria um novo procedimento no sistema")
    @PostMapping
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ProcedureInfoDTO> persist(@Validated @RequestBody(required = true) ProcedureInfoDTO request,
                                                    HttpServletResponse response) {
        ProcedureInfoDTO createdProcedure = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProcedure);
    }

    @ApiOperation(nickname = "procedure-put", value = "Atualiza um procedimento existente no sistema")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ProcedureInfoDTO> update(@PathVariable("id") Long id,
                                                   @Validated @RequestBody(required = true) ProcedureInfoDTO request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @ApiOperation(nickname = "procedure-delete-id", value = "Deleta um procedimento pelo identificador")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
