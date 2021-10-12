package br.com.nivlabs.gp.controller;

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

import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.ReportGenerationRequestDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutInfoDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe ReportController.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@Api("Endpoint - Gerador de relatórios")
@RestController
@RequestMapping(value = "/jasperReportCreator")
public class ReportController extends BaseController<ReportService> {

    @ApiOperation(nickname = "jasperReportCreator-layout-get-page", value = "Busca uma página de layouts de relatórios")
    @GetMapping
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<ReportLayoutDTO>> findList(CustomFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.findPageOfReportLayout(pageSettings));
    }

    @ApiOperation(nickname = "jasperReportCreator-layout-get-id", value = "Busca um layout de relatório baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<ReportLayoutInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findReportLayoutById(id));
    }

    @ApiOperation(nickname = "jasperReportCreator-layout-post", value = "Insere um novo layout de relatório")
    @PostMapping()
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<ReportLayoutInfoDTO> persist(@Validated @RequestBody(required = true) FileDTO file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newReporLayout(file));
    }

    @ApiOperation(nickname = "jasperReportCreator-layout-post-id", value = "Gera um relatório a partir de um layout")
    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<DigitalDocumentDTO> generateReport(@Validated @RequestBody(required = true) ReportGenerationRequestDTO reportParam,
                                                             @NotNull @PathVariable("id") Long id) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.generateDocumentFromReportLayout(id, reportParam));
    }

    @ApiOperation(nickname = "jasperReportCreator-layout-delete", value = "Deleta um layout de relatório baseado no identificador")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteLayoutById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(nickname = "jasperReportCreator-layout-put", value = "Atualiza um layout de relatório")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RELATORIO_ESCRITA', 'RELATORIO_LEITURA', 'ADMIN')")
    public ResponseEntity<ReportLayoutInfoDTO> update(@PathVariable("id") Long id,
                                                      @Validated @RequestBody(required = true) FileDTO file) {
        return ResponseEntity.ok().body(service.update(id, file));
    }

}
