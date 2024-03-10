package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Público alvo")
public class TargetAudienceDTO extends DataTransferObjectBase {

    @Schema(description = "Descrição do público alvo")
    private String description;

    public TargetAudienceDTO() {
    }

    public TargetAudienceDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TargetAudienceDTO{" +
                "description='" + description + '\'' +
                '}';
    }
}
