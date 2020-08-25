package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Alergia")
public class AllergyDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8445432329934001912L;

    @ApiModelProperty("Identificador único da alergia")
    private Long id;

    @ApiModelProperty("Componente alérgico")
    @NotNull(message = "O nome do componente da alergia deve ser informado")
    private String description;

    public AllergyDTO() {
        super();
    }

    public AllergyDTO(Long id, String description) {
        super();
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AllergyDTO [id=" + id + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        AllergyDTO other = (AllergyDTO) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
