package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Serviços disponíveis")
public class AvailableServiceDTO extends DataTransferObjectBase {

    @Schema(description = "Nome do serviço")
    private String name;
    @Schema(description = "Descrição do serviço")
    private String description;

    public AvailableServiceDTO() {
    }

    public AvailableServiceDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AvailableServiceDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
