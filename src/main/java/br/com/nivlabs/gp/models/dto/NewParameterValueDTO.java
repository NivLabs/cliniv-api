package br.com.nivlabs.gp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requisição de novo parâmetro")
public class NewParameterValueDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1842321490525613622L;

    private String newValue;

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return "NewParameterValueDTO [newValue=" + newValue + "]";
    }

}
