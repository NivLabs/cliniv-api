package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.controller.filters.UserFilters;
import br.com.ft.gdp.models.dto.UserDTO;
import br.com.ft.gdp.models.dto.UserInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.service.UserService;
import br.com.ft.gdp.service.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe SpecialityController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de jan de 2020
 */
@Api("Endpoint - Usuário")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @ApiOperation(nickname = "user-get", value = "Busca uma página de usuários baseada em filtros")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findPage(UserFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(userService.searchEntityPage(filters, pageSettings));
    }

    @ApiOperation(nickname = "user-get-id", value = "Busca um usuário baseado no ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @ApiOperation(nickname = "user-put", value = "Atualiza dados do usuário selecionado")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> update(@PathVariable(name = "id") Long id, @Validated @RequestBody UserInfoDTO entity) {
        return ResponseEntity.ok(userService.update(id, entity));
    }

    @ApiOperation(nickname = "user-post", value = "Cria o cadastro de usuário")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> create(@Validated @RequestBody UserInfoDTO entity) {
        return ResponseEntity.ok(userService.create(entity));
    }

    @ApiOperation(nickname = "user-get-by-document", value = "Busca um usuário pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> findByDocument(@PathVariable("documentType") DocumentType documentType,
                                                      @PathVariable("document") String document) {
        return ResponseEntity.ok(userService.findByCpf(document));
    }

    @ApiOperation(nickname = "user-reset-password", value = "Reseta a senha do usuário")
    @PutMapping("{id}/reset-password")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> resetPassword(@PathVariable("id") Long id) {
        authService.resetPassword(id);
        return ResponseEntity.noContent().build();
    }
}
