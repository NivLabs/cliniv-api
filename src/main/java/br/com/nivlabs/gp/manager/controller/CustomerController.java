package br.com.nivlabs.gp.manager.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.BaseController;
import br.com.nivlabs.gp.manager.dto.NewCustomerDTO;
import br.com.nivlabs.gp.manager.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Paciente", description = "Endpoint - Operações com Paciente")
@RestController
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController<CustomerService> {

    @Operation(summary = "customer-post", description = "Insere um novo paciente na aplicação")
    @PostMapping
    public ResponseEntity<Void> persist(@Validated @RequestBody(required = true) NewCustomerDTO request,
                                        HttpServletResponse response) {
        service.persist(request);
        return ResponseEntity.noContent().build();

    }
}
