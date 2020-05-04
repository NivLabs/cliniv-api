package br.com.ft.gdp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.config.security.UserOfSystem;
import br.com.ft.gdp.models.dto.ForgotPasswordRequestDTO;
import br.com.ft.gdp.models.dto.NewPasswordRequestDTO;
import br.com.ft.gdp.service.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Classe AuthController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Api(value = "Endpoint - Auth - Autorização")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Gera nova senha de acesso.
     * 
     * OBS: NÃO IMPLEMENTAR PRÉ-AUTORIZAÇÃO (@PreAuthorize)
     * 
     * @param newPasswordDTO
     * @param response
     * @return
     */
    @ApiOperation(nickname = "auth-forgot", value = "Cria uma nova senha em caso de esquecimento")
    @PutMapping("/forgot")
    public ResponseEntity<Void> update(@ApiParam(name = "ForgotPasswordRequest", value = "Objeto de requisição para alteração de senha") @Validated @RequestBody(required = true) ForgotPasswordRequestDTO forgotPasswordDTO,
                                       HttpServletResponse response) {
        authService.createNewPassword(forgotPasswordDTO);
        return ResponseEntity.noContent().build();

    }

    /**
     * Altera a senha de acesso.
     * 
     * 
     * @param newPasswordDTO
     * @param response
     * @return
     */
    @ApiOperation(nickname = "auth-new-password", value = "Altera a senha de acesso")
    @PutMapping("/password")
    public ResponseEntity<Void> update(@ApiParam(name = "NewPasswordRequest", value = "Objeto de requisição para alteração de senha, o usuário da sessão ativa é utilizado") @Validated @RequestBody(required = true) NewPasswordRequestDTO newPasswordDTO,
                                       HttpServletResponse response) {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        authService.updatePassword(newPasswordDTO, userFromSession);
        return ResponseEntity.noContent().build();

    }

}
