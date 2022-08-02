package br.com.nivlabs.cliniv.controller.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.models.dto.NewDynamicFormAnsweredDTO;
import br.com.nivlabs.cliniv.service.dynamicform.DynamicFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Formulários Dinâmicos", description = "Endpoint - Formulários dinâmicos do atendimento")
@RestController
@RequestMapping(value = "/attendance")
public class DynamicFormIntoAttencance {

    @Autowired
    private DynamicFormService service;

    @Operation(summary = "dynamic-form-into-attendance-post", description = "Insere um novo formulário respondido na aplicação")
    @PostMapping("/{idAttendance}/dynamic-form")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<NewDynamicFormAnsweredDTO> persist(@PathVariable("idAttendance") Long idAttendance,
                                                             @Validated @RequestBody(required = true) NewDynamicFormAnsweredDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.newDynamicFormAnswered(idAttendance, request));
    }

}
