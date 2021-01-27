package br.com.nivlabs.gp.models.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe SpecialityInfoDTO.java Representa a informação detalhada da especialidade.
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 10 de jan de 2020
 */

@ApiModel("Informações da especialidade")
public class SpecialityInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4419810660458356444L;

    @ApiModelProperty("Identificador único da especialidade")
    private Long id;

  @ApiModelProperty("Nome da especialidade")
    @NotNull(message = "Informe o nome da especialidade")
    private String name;

    @ApiModelProperty("Descrição da especialidade")
    private String description;

    private List<ResponsibleDTO> responsibles = new ArrayList<>();


    public SpecialityInfoDTO() {
        super();
    }

    public SpecialityInfoDTO(Long id, String name, String description, List<ResponsibleDTO> responsibles) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.responsibles = responsibles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ResponsibleDTO> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<ResponsibleDTO> responsibles) {
        this.responsibles = responsibles;
    }

    @Override
    public String toString() {
        return "SpecialityInfoDTO [id=" + id + ", name=" + name + ", description=" + description + ", responsibles=" + responsibles + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((responsibles == null) ? 0 : responsibles.hashCode());
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
        SpecialityInfoDTO other = (SpecialityInfoDTO) obj;
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (responsibles == null) {
            if (other.responsibles != null)
                return false;
        } else if (!responsibles.equals(other.responsibles))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SpecialityInfoDTO [id=" + id + ", description=" + description + ", responsibles=" + responsibles + "]";
    }
}
