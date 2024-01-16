package br.com.nivlabs.cliniv.models.dto;

import jakarta.validation.constraints.NotNull;

import br.com.nivlabs.cliniv.enums.MetaType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Questão do formulário dinâmico")
public class DynamicFormQuestionDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -1666755500493520346L;

    @Schema(description = "Identificador único da questão do formulário dinâmico")
    private Long id;

    @Schema(description = "Questão do formulário dinâmico")
    @NotNull(message = "Informe a questão")
    private String question;

    @Schema(description = "Tipo da questão")
    @NotNull(message = "Informe o tipo da questão")
    private MetaType metaType;

    public DynamicFormQuestionDTO() {
        super();
    }

    public DynamicFormQuestionDTO(Long id, @NotNull(message = "Informe a questão") String question,
            @NotNull(message = "Informe o tipo da questão") MetaType metaType) {
        super();
        this.id = id;
        this.question = question;
        this.metaType = metaType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public MetaType getMetaType() {
        return metaType;
    }

    public void setMetaType(MetaType metaType) {
        this.metaType = metaType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metaType == null) ? 0 : metaType.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
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
        DynamicFormQuestionDTO other = (DynamicFormQuestionDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (metaType != other.metaType)
            return false;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DynamicFormQuestionDTO [id=" + id + ", question=" + question + ", metaType=" + metaType + "]";
    }

}
