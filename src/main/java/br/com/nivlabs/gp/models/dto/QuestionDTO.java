package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Questão do formulário dinâmico")
public class QuestionDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -7700694137849034946L;

    @Schema(description = "Questão do formulário dinâmico")
    @NotBlank(message = "Informar a questão do formulário é obrigatório.")
    private DynamicFormQuestionDTO dynamicFormQuestion;

    @Schema(description = "Resposta da questão")
    @NotBlank(message = "Informar a resposta é obrigatório.")
    private String response;

    public QuestionDTO() {
        super();
    }

    public QuestionDTO(
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
        QuestionDTO other = (QuestionDTO) obj;
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
