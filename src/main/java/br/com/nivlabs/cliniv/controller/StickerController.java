package br.com.nivlabs.cliniv.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.models.dto.StickerDTO;
import br.com.nivlabs.cliniv.service.sticker.StickerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lembretes", description = "Endpoint - Lembretes")
@RestController
@RequestMapping(value = "/sticker")
public class StickerController extends BaseController<StickerService> {

    @Operation(summary = "sticker-post", description = "Cria um novo lembrete no sistema")
    @PostMapping
    public ResponseEntity<StickerDTO> persist(@Validated @RequestBody(required = true) StickerDTO request,
                                              HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "sticker-put", description = "Atualiza um lembrete existente no sistema")
    @PutMapping("/{id}")
    public ResponseEntity<StickerDTO> update(@PathVariable("id") Long id,
                                             @Validated @RequestBody(required = true) StickerDTO request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @Operation(summary = "sticker-delete-id", description = "Deleta um lembrete pelo identificador")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
