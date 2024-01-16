package br.com.nivlabs.cliniv.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.models.dto.ChangePasswordByForgotPasswordRequestDTO;
import br.com.nivlabs.cliniv.models.dto.CredentialsDTO;
import br.com.nivlabs.cliniv.models.dto.NewPasswordRequestDTO;
import br.com.nivlabs.cliniv.service.security.SecurityService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe AuthController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Tag(name = "Autorização", description = "Endpoint - Auth - Autorização")
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController<SecurityService> {

    private static final String HEADER_ACCESS_CONTROL_EXPOSE_NAME = "access-control-expose-headers";
    private static final String HEADER_AUTHORIZATION_NAME = "Authorization";

    @Operation(summary = "login-post", description = "Realiza o Login na aplicação")
    @PostMapping
    public ResponseEntity<Void> login(@RequestBody @Validated CredentialsDTO credentials, HttpServletRequest request,
                                      HttpServletResponse response) {
        String token = service.login(credentials, request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY));
        response.addHeader(HEADER_AUTHORIZATION_NAME, "Bearer " + token);
        response.addHeader(HEADER_ACCESS_CONTROL_EXPOSE_NAME, HEADER_AUTHORIZATION_NAME);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gera nova senha de acesso.
     * 
     * OBS: NÃO IMPLEMENTAR PRÉ-AUTORIZAÇÃO (@PreAuthorize)
     * 
     * @param newPasswordDTO
     * @return
     */
    @Operation(summary = "auth-forgot", description = "Cria uma nova senha em caso de esquecimento")
    @PutMapping("/forgot")
    public ResponseEntity<Void> update(@Validated @RequestBody(required = true) ChangePasswordByForgotPasswordRequestDTO forgotPasswordDTO) {
        service.createNewPasswordByForgotPassword(forgotPasswordDTO);
        return ResponseEntity.noContent().build();

    }

    /**
     * Altera a senha de acesso.description
     * 
     * OBS: NÃO IMPLEMENTAR PRÉ-AUTORIZAÇÃO (@PreAuthorize)
     * 
     * @param newPasswordDTO
     * @return
     */
    @Operation(summary = "auth-new-password", description = "Altera a senha de acesso")
    @PutMapping("/password")
    public ResponseEntity<Void> update(@Validated @RequestBody(required = true) NewPasswordRequestDTO newPasswordDTO) {
        service.updatePassword(newPasswordDTO);
        return ResponseEntity.noContent().build();

    }

}
