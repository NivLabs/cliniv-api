package br.com.nivlabs.gp.controller.attendance;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.models.dto.NewDynamicFormAnsweredDTO;
import br.com.nivlabs.gp.service.DynamicFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint - Formulários dinâmicos do atendimento")
@RestController
@RequestMapping(value = "/attendance")
public class DynamicFormIntoAttencance {

    @Autowired
    private DynamicFormService service;

    @ApiOperation(nickname = "dynamic-form-into-attendance-post", value = "Insere um novo formulário respondido na aplicação")
    @PostMapping("/{idAttendance}/dynamic-form")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<NewDynamicFormAnsweredDTO> persist(@PathVariable("idAttendance") Long idAttendance,
                                                             @Validated @RequestBody(required = true) NewDynamicFormAnsweredDTO request,
                                                             HttpServletResponse response) {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.newAnamnesisResponse(idAttendance, request, userFromSession.getUsername()));
    }

}
