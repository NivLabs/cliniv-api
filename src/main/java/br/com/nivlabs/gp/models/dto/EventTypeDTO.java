package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.domain.EventType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Classe EventTypeDTO.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@ApiModel(description = "Tipo de Evento")
public class EventTypeDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2370290606342755763L;

    @ApiModelProperty("Identificador único do tipo de evento")
    private Long id;

    @ApiModelProperty("Nome do tipo de evento")
    @NotBlank(message = "Informar o NOME  do tipo de evento é obrigatório")
    @Size(min = 3, max = 45, message = "O nome do evento deve conter ao menos três letras")
    private String name;

    @ApiModelProperty("Descrição do tipo de evento")
    @Size(min = 3, max = 200, message = "A descricao do tipo de evento deve conter ao menos três letras")
    private String description;

    public EventTypeDTO(Long id) {
        this.id = id;
    }

    public EventTypeDTO(Long id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public EventTypeDTO() {
        super();
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

    @Override
    public String toString() {
        return "EventTypeDTO [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        EventTypeDTO other = (EventTypeDTO) obj;
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
        return true;
    }

    @JsonIgnore
    public EventType getEventTypeDomainFromDTO() {
        EventType domain = new EventType();

        domain.setId(getId());
        domain.setName(getName());
        domain.setDescription(getDescription());
        return domain;
    }
}
