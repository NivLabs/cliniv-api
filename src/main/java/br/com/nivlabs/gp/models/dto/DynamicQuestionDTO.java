package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Questão do formulário dinâmico")
public class DynamicQuestionDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -7700694137849034946L;

    @NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.")
    @ApiModelProperty("Item da anamese com pertunga e tipo da resposta")
    private DynamicFormQuestionDTO dynamicFormQuestion;

    @NotBlank(message = "Informar a resposta é obrigatório.")
    @ApiModelProperty("Resposta")
    private String response;

    public DynamicQuestionDTO() {
        super();
    }

    public DynamicQuestionDTO(
            @NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.") DynamicFormQuestionDTO dynamicFormQuestion,
            @NotBlank(message = "Informar a resposta é obrigatório.") String response) {
        super();
        this.dynamicFormQuestion = dynamicFormQuestion;
        this.response = response;
    }

    public DynamicFormQuestionDTO getDynamicFormQuestion() {
        return dynamicFormQuestion;
    }

    public void setDynamicFormQuestion(DynamicFormQuestionDTO dynamicFormQuestion) {
        this.dynamicFormQuestion = dynamicFormQuestion;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "DynamicQuestionDTO [dynamicFormQuestion=" + dynamicFormQuestion + ", response=" + response + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dynamicFormQuestion == null) ? 0 : dynamicFormQuestion.hashCode());
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
        if (dynamicFormQuestion == null) {
            if (other.dynamicFormQuestion != null)
                return false;
        } else if (!dynamicFormQuestion.equals(other.dynamicFormQuestion))
            return false;
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        return true;
    }

}
