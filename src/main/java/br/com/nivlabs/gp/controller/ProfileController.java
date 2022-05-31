package br.com.nivlabs.gp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.service.userservice.UserService;
import br.com.nivlabs.gp.util.SecurityContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe ProfileController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */
@Tag(name = "Perfil", description = "Endpoint - Perfil")
@RestController
@RequestMapping(value = "/profile")
public class ProfileController extends BaseController<UserService> {

    @Operation(summary = "profile-get", description = "Busca dados do perfil do usuário logado")
    @GetMapping
    public ResponseEntity<UserInfoDTO> getMe() {
        return ResponseEntity.ok(service.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername()));
    }

    @Operation(summary = "profile-put", description = "Atualiza dados do perfil do usuário logado")
    @PutMapping
    public ResponseEntity<UserInfoDTO> updateMe(@Validated @RequestBody UserInfoDTO entity) {
        return ResponseEntity.ok(service.updateProfile(entity));
    }

}
