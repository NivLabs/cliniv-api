package br.com.nivlabs.gp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.exception.InvalidOperationException;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.service.UserService;
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
public class ProfileController {

    @Autowired
    private UserService userService;

    @ApiOperation(nickname = "profile-get", value = "Busca dados do perfil do usuário logado")
    @GetMapping
    public ResponseEntity<UserInfoDTO> getMe() {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.findByUserName(userFromSession.getUsername()));
    }

    @ApiOperation(nickname = "profile-put", value = "Atualiza dados do perfil do usuário logado")
    @PutMapping("/{id}")
    public ResponseEntity<UserInfoDTO> updateMe(@PathVariable(name = "id") Long id, @Validated @RequestBody UserInfoDTO entity) {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        validUserToUpdate(entity, userFromSession);

        UserInfoDTO userUpdated = userService.updateFromDto(id, entity);
        return ResponseEntity.ok(userUpdated);
    }

    /**
     * 
     * Verifica se o usuário que está sendo alterado é o mesmo da sessão
     * 
     * @param entity
     * @param userFromSession
     */
    private void validUserToUpdate(UserInfoDTO entity, UserOfSystem userFromSession) {
        if (!userFromSession.getUsername().equals(entity.getUserName()))
            throw new InvalidOperationException("Operação não permitida, você só pode editar o seu próprio perfil");
    }

}
