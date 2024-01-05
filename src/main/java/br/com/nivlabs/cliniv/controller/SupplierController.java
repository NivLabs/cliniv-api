package br.com.nivlabs.cliniv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.service.supplier.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Fornecedor", description = "Endpoint - Operações com Fornecedor")
@RestController
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController<SupplierService> {

    @Autowired
    private ApplicationEventPublisher publisher;
}
