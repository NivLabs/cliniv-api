package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.config.security.UserOfSystem;
import br.com.ft.gdp.models.dto.UserInfoDTO;
import br.com.ft.gdp.service.UserService;
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
    private UserService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "profile-get", value = "Busca dados do perfil do usuário logado")
    @GetMapping
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<UserInfoDTO> getMe() {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.findByUsername(userFromSession.getUsername()).getUserInfoDTO());
    }
}
