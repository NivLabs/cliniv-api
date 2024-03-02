package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Plano/Convênio de saúde")
public class AcceptedInsurancePlanDTO extends DataTransferObjectBase {

    @Schema(description = "Nome do Plano/Convênio de saúde")
    private String name;

    public AcceptedInsurancePlanDTO() {
    }

    public AcceptedInsurancePlanDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AcceptedInsurancePlanDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}

