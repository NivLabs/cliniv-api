package br.com.nivlabs.cliniv.controller;

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

import br.com.nivlabs.cliniv.controller.filters.UserFilters;
import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.models.dto.UserDTO;
import br.com.nivlabs.cliniv.models.dto.UserInfoDTO;
import br.com.nivlabs.cliniv.service.security.SecurityService;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe SpecialityController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de jan de 2020
 */
@Tag(name = "Usuário", description = "Endpoint - Usuário")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController<UserService> {

    @Autowired
    private SecurityService authService;

    @Operation(summary = "user-get", description = "Busca uma página de usuários baseada em filtros")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findPage(UserFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.searchEntityPage(filters, pageSettings));
    }

    @Operation(summary = "user-get-id", description = "Busca um usuário baseado no ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "user-put", description = "Atualiza dados do usuário selecionado")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> update(@PathVariable(name = "id") Long id, @Validated @RequestBody UserInfoDTO entity) {
        return ResponseEntity.ok(service.userUpdate(id, entity));
    }

    @Operation(summary = "user-post", description = "Cria o cadastro de usuário")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> create(@Validated @RequestBody UserInfoDTO entity) {
        return ResponseEntity.ok(service.create(entity));
    }

    @Operation(summary = "user-get-by-document", description = "Busca um usuário pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserInfoDTO> findByDocument(@PathVariable("documentType") DocumentType documentType,
                                                      @PathVariable("document") String document) {
        return ResponseEntity.ok(service.findByCpf(document));
    }

    @Operation(summary = "user-reset-password", description = "Reseta a senha do usuário")
    @PutMapping("{id}/reset-password")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> resetPassword(@PathVariable("id") Long id) {
        authService.resetPassword(id);
        return ResponseEntity.noContent().build();
    }
}
