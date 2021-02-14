
/**
 * 
 */
package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.DynamicFormFilters;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.service.DynamicFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Formulários dinâmicos da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Api(value = "Endpoint - Formulários Dinâmicos")
@RestController
@RequestMapping("/dynamic-form")
public class DynamicFormController {

    @Autowired
    private DynamicFormService service;

    @ApiOperation(nickname = "dynamic-form-get-id", value = "Busca um formulário de anamnese baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'FORMULARIO_ESCRITA', 'FORMULARIO_LEITURA', 'ADMIN')")
    public ResponseEntity<DynamicFormDTO> findFormById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findFormById(id));
    }

    @ApiOperation(nickname = "dynamic-form-page", value = "Busca uma anamnese baseada no identificador")
    @GetMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'FORMULARIO_ESCRITA', 'FORMULARIO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<DynamicFormDTO>> findPageOfAnamnesisForms(DynamicFormFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.findPageOfDymicaForm(filters, pageSettings));
    }

    @ApiOperation(nickname = "dynamic-form-delete-id", value = "Deleta um formulário dinâmico pelo identificador")
    @DeleteMapping
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteFormById(id);
        return ResponseEntity.noContent().build();
    }

}
