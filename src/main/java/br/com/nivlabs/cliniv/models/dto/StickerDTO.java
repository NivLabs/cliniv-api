package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sticker")
public class StickerDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2768141981922488166L;

    @Schema(description = "Identificador único do lembrete")
    private Long id;

    @Schema(description = "Data de criação do lembrete")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Schema(description = "Texto do lembrete")
    @Size(max = 150, message = "A descrição do lembrete não deve ser maior que 150 caracteres")
    private String description;

    public StickerDTO() {
        super();
    }

    public StickerDTO(Long id, LocalDateTime createdAt, String description) {
        super();
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StickerDTO [id=");
        builder.append(id);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

}
