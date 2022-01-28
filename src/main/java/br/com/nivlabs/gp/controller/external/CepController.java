package br.com.nivlabs.gp.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.service.external.CEPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CEP", description = "Endpoint - Serviços de operações com CEP")
@RestController
@RequestMapping("/cep")
public class CepController {

    @Autowired
    private CEPService service;

    @Operation(summary = "cep-get", description = "Busca CEP em API externa")
    @GetMapping("/{cep}")
    public ResponseEntity<AddressDTO> get(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(service.get(cep));

    }
}
