
/**
 * 
 */
package br.com.nivlabs.gp.controller.dynamicform;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.event.CreatedResourceEvent;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.service.DynamicFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Formulários dinâmicos da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Api(value = "Endpoint - Questões dos Formulários Dinâmicos")
@RestController
@RequestMapping("/dynamic-form")
public class DynamicFormQuestionController {

    @Autowired
    private DynamicFormService service;
    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "question-delete-id", value = "Deleta um formulário dinâmico pelo identificador")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteQuestionById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(nickname = "question-post", value = "Insere um novo formulário dinâmico na aplicação")
    @PostMapping("/{idForm}/question")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DynamicFormQuestionDTO> create(@Validated @RequestBody(required = true) DynamicFormQuestionDTO request,
                                                         @PathVariable("idForm") Long idForm,
                                                         HttpServletResponse response) {
        DynamicFormQuestionDTO createdPatient = service.createQuestion(idForm, request);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdPatient.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);

    }

    @ApiOperation(nickname = "question-put", value = "Atualiza um formulário na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMULARIO_ESCRITA', 'ADMIN')")
    public ResponseEntity<DynamicFormQuestionDTO> update(@PathVariable("id") Long id,
                                                         @Validated @RequestBody(required = true) DynamicFormQuestionDTO request) {
        return ResponseEntity.ok().body(service.updateQuestion(id, request));
    }

}