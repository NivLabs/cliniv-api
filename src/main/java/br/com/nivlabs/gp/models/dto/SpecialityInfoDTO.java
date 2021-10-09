package br.com.nivlabs.gp.models.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

@ApiModel(description = "Informações da especialidade")
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
    public int hashCode() {
        return Objects.hash(description, id, name, responsibles);
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
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(responsibles, other.responsibles);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SpecialityInfoDTO [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", responsibles=");
        builder.append(responsibles);
        builder.append("]");
        return builder.toString();
    }

}
