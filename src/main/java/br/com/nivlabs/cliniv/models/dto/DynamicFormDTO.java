package br.com.nivlabs.cliniv.models.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Formulário dinâmico")
public class DynamicFormDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6122041136613519207L;

    @Schema(description = "Identificador único do formulário, não é obrigatório para a inserção")
    private Long id;

    @NotBlank(message = "Informar do título do formulário é obrigatório")
    @Schema(description = "Descrição do título do formulário")
    private String title;

    @Schema(description = "Questões do formulário dinâmico")
    private List<DynamicFormQuestionDTO> questions = new ArrayList<>();

    public DynamicFormDTO() {
        super();
    }

    public DynamicFormDTO(Long id, String title, List<DynamicFormQuestionDTO> questions) {
        super();
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public DynamicFormDTO(Long id, String title) {
        this.id = id;
        this.title = title;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DynamicFormQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DynamicFormQuestionDTO> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "DynamicFormDTO [id=" + id + ", title=" + title + ", questions=" + questions + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((questions == null) ? 0 : questions.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        DynamicFormDTO other = (DynamicFormDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (questions == null) {
            if (other.questions != null)
                return false;
        } else if (!questions.equals(other.questions))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}
