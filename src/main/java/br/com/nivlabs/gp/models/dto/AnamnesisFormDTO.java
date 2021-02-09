package br.com.nivlabs.gp.models.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Formulário de Anamnese")
public class AnamnesisFormDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6122041136613519207L;

    @ApiModelProperty("Identificador único do formulário de anamnese, não é obrigatório para a inserção")
    private Long id;

    @NotBlank(message = "Informar do título do formulário é obrigatório")
    @ApiModelProperty("Descrição do título do formulário de anamnese")
    private String title;

    @ApiModelProperty("Questões do formulário de anamnese")
    private List<AnamnesisItemDTO> questions = new ArrayList<>();

    public AnamnesisFormDTO() {
        super();
    }

    public AnamnesisFormDTO(Long id, String title, List<AnamnesisItemDTO> questions) {
        super();
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public AnamnesisFormDTO(Long id, String title) {
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

    public List<AnamnesisItemDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AnamnesisItemDTO> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "AnamnesisFormDTO [id=" + id + ", title=" + title + ", questions=" + questions + "]";
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
        AnamnesisFormDTO other = (AnamnesisFormDTO) obj;
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
