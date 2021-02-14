package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Questão do formulário dinâmico")
public class DynamicQuestionDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -7700694137849034946L;

    @NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.")
    @ApiModelProperty("Item da anamese com pertunga e tipo da resposta")
    private DynamicFormQuestionDTO anamnesisItem;

    @NotBlank(message = "Informar a resposta é obrigatório.")
    @ApiModelProperty("Resposta")
    private String response;

    public DynamicQuestionDTO() {
        super();
    }

    public DynamicQuestionDTO(
            @NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.") DynamicFormQuestionDTO anamnesisItem,
            @NotBlank(message = "Informar a resposta é obrigatório.") String response) {
        super();
        this.anamnesisItem = anamnesisItem;
        this.response = response;
    }

    public DynamicFormQuestionDTO getAnamnesisItem() {
        return anamnesisItem;
    }

    public void setAnamnesisItem(DynamicFormQuestionDTO anamnesisItem) {
        this.anamnesisItem = anamnesisItem;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anamnesisItem == null) ? 0 : anamnesisItem.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DynamicQuestionDTO other = (DynamicQuestionDTO) obj;
        if (anamnesisItem == null) {
            if (other.anamnesisItem != null)
                return false;
        } else if (!anamnesisItem.equals(other.anamnesisItem))
            return false;
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DynamicQuestionDTO [anamnesisItem=" + anamnesisItem + ", response=" + response + "]";
    }

}
