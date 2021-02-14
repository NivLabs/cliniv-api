
/**
 * 
 */
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.DynamicFormFilters;
import br.com.nivlabs.gp.event.CreatedResourceEvent;
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
    @Autowired
    private ApplicationEventPublisher publisher;

    /**************************************
     ** Operações com Formulário completo *
     **************************************/

    /**
     * Busca informações detalhadas de um formulário dinâmico
     * 
     * @param id Identificador único do formulário
     * @return Informações detalhadas de um formulário dinâmico
     */
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

    @ApiOperation(nickname = "dynamic-form-post", value = "Insere um novo formulário dinâmico na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DynamicFormDTO> persist(@Validated @RequestBody(required = true) DynamicFormDTO request,
                                                  HttpServletResponse response) {
        DynamicFormDTO createdPatient = service.create(request);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdPatient.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);

    }

    /***************************************
     *** Operações com itens do Formulário *
     ***************************************/

}
