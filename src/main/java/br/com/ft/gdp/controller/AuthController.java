package br.com.ft.gdp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.models.dto.NewPasswordRequestDTO;
import br.com.ft.gdp.service.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe AuthController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Api("Endpoint - Auth - Autorização")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(nickname = "auth-forgot", value = "Cria uma nova senha em caso de esquecimento")
    @PutMapping("/forgot")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @Validated @RequestBody(required = true) NewPasswordRequestDTO newPasswordDTO,
                                       HttpServletResponse response) {
        authService.createNePassword(newPasswordDTO);
        return ResponseEntity.noContent().build();

    }
}
