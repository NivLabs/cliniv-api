package br.com.tl.gdp.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tl.gdp.models.dto.AddressDTO;
import br.com.tl.gdp.service.external.CEPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Endpoint - CEP")
@RestController
@RequestMapping("/cep")
public class CepController {

    @Autowired
    private CEPService service;

    @ApiOperation(nickname = "cep-get", value = "Busca CEP em API externa")
    @GetMapping("/{cep}")
    public ResponseEntity<AddressDTO> get(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(service.get(cep));

    }
}
