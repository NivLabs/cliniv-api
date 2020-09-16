package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.AccommodationType;
import io.swagger.annotations.ApiModel;

@ApiModel("Sala (ambulatório) ou Leito")
public class AccommodationDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1574668174638044648L;

    private Long id;

    @NotNull(message = "Informe o identificador do setor de cadastro")
    private Long sectorId;

    @NotNull(message = "Informe a descrição da sala ou leito")
    private String description;

    @NotNull(message = "Informe se é sala (ambulatório) ou leito")
    private AccommodationType type;

    public AccommodationDTO(Long id) {
        super();
        this.id = id;
    }

    public AccommodationDTO(Long id, Long sectorId, String description, AccommodationType type) {
        super();
        this.id = id;
        this.sectorId = sectorId;
        this.description = description;
        this.type = type;
    }

    public AccommodationDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccommodationType getType() {
        return type;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((sectorId == null) ? 0 : sectorId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        AccommodationDTO other = (AccommodationDTO) obj;
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
        if (sectorId == null) {
            if (other.sectorId != null)
                return false;
        } else if (!sectorId.equals(other.sectorId))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AccommodationDTO [id=" + id + ", sectorId=" + sectorId + ", description=" + description + ", type=" + type + "]";
    }

}
