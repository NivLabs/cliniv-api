package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Informações do setor e acomodações
 *
 * @author viniciosarodrigues
 * @since 06-10-2021
 *
 */
@ApiModel(description = "Informações do Setor")
public class SectorInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8018406138528606923L;

    @ApiModelProperty("Identificador único do setor")
    private Long id;

    @ApiModelProperty("Descrição do setor")
    private String description;

    @ApiModelProperty("Lista de salas e leitos do setor (Acomodações)")
    private List<AccommodationDTO> listOfRoomsOrBeds = new ArrayList<>();

    @ApiModelProperty("Data / Hora de criação do setor")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime createdAt;

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

    public List<AccommodationDTO> getListOfRoomsOrBeds() {
        return listOfRoomsOrBeds;
    }

    public void setListOfRoomsOrBeds(List<AccommodationDTO> listOfRoomsOrBeds) {
        this.listOfRoomsOrBeds = listOfRoomsOrBeds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((listOfRoomsOrBeds == null) ? 0 : listOfRoomsOrBeds.hashCode());
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
        SectorInfoDTO other = (SectorInfoDTO) obj;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
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
        if (listOfRoomsOrBeds == null) {
            if (other.listOfRoomsOrBeds != null)
                return false;
        } else if (!listOfRoomsOrBeds.equals(other.listOfRoomsOrBeds))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SectorInfoDTO [id=" + id + ", description=" + description + ", listOfRoomsOrBeds=" + listOfRoomsOrBeds
                + ", createdAt=" + createdAt + "]";
    }

}
