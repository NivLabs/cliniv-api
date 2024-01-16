package br.com.nivlabs.cliniv.manager.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.manager.dto.NewCustomerDTO;
import br.com.nivlabs.cliniv.manager.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Paciente", description = "Endpoint - Operações com Paciente")
@RestController
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController<CustomerService> {

    @Operation(summary = "customer-post", description = "Insere um novo cliente na base de pré-cadastro")
    @PostMapping
    public ResponseEntity<Void> persist(@Validated @RequestBody(required = true) NewCustomerDTO request,
                                        HttpServletResponse response) {
        service.persist(request);
        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "customer-get-confirm", description = "Confirma a criação de um novo cliente na base de pré-cadastro")
    @GetMapping("/confirm/{uuid}")
    public void confirmUuid(@PathVariable("uuid") String uuid, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", service.confirmUUID(uuid));
        httpServletResponse.setStatus(302);
    }
}
