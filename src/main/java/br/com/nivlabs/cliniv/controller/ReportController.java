package br.com.nivlabs.cliniv.controller;

import javax.validation.constraints.NotNull;

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

import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.models.dto.FileDTO;
import br.com.nivlabs.cliniv.models.dto.ReportGenerationRequestDTO;
import br.com.nivlabs.cliniv.models.dto.ReportLayoutDTO;
import br.com.nivlabs.cliniv.models.dto.ReportLayoutInfoDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.service.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe ReportController.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@Tag(name = "Relatórios", description = "Endpoint - Gerador de relatórios")
@RestController
@RequestMapping(value = "/report")
public class ReportController extends BaseController<ReportService> {

    @Operation(summary = "report-layout-get-page", description = "Busca uma página de layouts de relatórios")
    @GetMapping
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<ReportLayoutDTO>> findList(CustomFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.findPageOfReportLayout(pageSettings));
    }

    @Operation(summary = "report-layout-get-id", description = "Busca um layout de relatório baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<ReportLayoutInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findReportLayoutById(id));
    }

    @Operation(summary = "report-layout-post", description = "Insere um novo layout de relatório")
    @PostMapping
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ReportLayoutInfoDTO> persist(@Validated @RequestBody(required = true) FileDTO file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newReporLayout(file));
    }

    @Operation(summary = "report-layout-post-id", description = "Gera um relatório a partir de um layout")
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<DigitalDocumentDTO> generateReport(@Validated @RequestBody(required = true) ReportGenerationRequestDTO reportParam,
                                                             @NotNull @PathVariable("id") Long id) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.generateDocumentFromReportLayout(id, reportParam));
    }

    @Operation(summary = "report-layout-delete", description = "Deleta um layout de relatório baseado no identificador")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteLayoutById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "report-layout-put", description = "Atualiza um layout de relatório")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<ReportLayoutInfoDTO> update(@PathVariable("id") Long id,
                                                      @Validated @RequestBody(required = true) FileDTO file) {
        return ResponseEntity.ok().body(service.update(id, file));
    }

}
