package br.com.nivlabs.gp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.service.userservice.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe ProfileController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */
@Api("Endpoint - Perfil")
@RestController
@RequestMapping(value = "/profile")
public class ProfileController extends BaseController<UserService> {

    @ApiOperation(nickname = "profile-get", value = "Busca dados do perfil do usuário logado")
    @GetMapping
    public ResponseEntity<UserInfoDTO> getMe() {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(service.findByUserName(userFromSession.getUsername()));
    }

    @ApiOperation(nickname = "profile-put", value = "Atualiza dados do perfil do usuário logado")
    @PutMapping
    public ResponseEntity<UserInfoDTO> updateMe(@Validated @RequestBody UserInfoDTO entity) {
        return ResponseEntity.ok(service.updateProfile(entity));
    }

}
