
/**
 * 
 */
package br.com.nivlabs.cliniv.controller.dynamicform;

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

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.controller.filters.DynamicFormFilters;
import br.com.nivlabs.cliniv.models.dto.DynamicFormDTO;
import br.com.nivlabs.cliniv.service.dynamicform.DynamicFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Formulários dinâmicos da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Tag(name = "Fomulários Dinâmicos", description = "Endpoint - Formulários Dinâmicos")
@RestController
@RequestMapping(value = "/dynamic-form")
public class DynamicFormController extends BaseController<DynamicFormService> {

    /**
     * Busca informações detalhadas de um formulário dinâmico
     * 
     * @param id Identificador único do formulário
     * @return Informações detalhadas de um formulário dinâmico
     */
    @Operation(summary = "dynamic-form-get-id", description = "Busca um formulário dinâmico baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'FORMULARIO_ESCRITA', 'FORMULARIO_LEITURA', 'ADMIN')")
    public ResponseEntity<DynamicFormDTO> findFormById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "dynamic-form-page", description = "Busca uma página de formulários dinâmicos")
    @GetMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'FORMULARIO_ESCRITA', 'FORMULARIO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<DynamicFormDTO>> findPageOfAnamnesisForms(DynamicFormFilters filters) {
        return ResponseEntity.ok(service.findPageOfDymicaForm(filters));
    }

    @Operation(summary = "dynamic-form-delete-id", description = "Deleta um formulário dinâmico pelo identificador")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteDynamicFormById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "dynamic-form-post", description = "Insere um novo formulário dinâmico na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DynamicFormDTO> persist(@Validated @RequestBody(required = true) DynamicFormDTO request,
                                                  HttpServletResponse response) {
        DynamicFormDTO createdPatient = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);

    }

    @Operation(summary = "dynamic-form-put", description = "Atualiza um formulário na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DynamicFormDTO> updateForm(@PathVariable("id") Long id,
                                                     @Validated @RequestBody(required = true) DynamicFormDTO request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

}
