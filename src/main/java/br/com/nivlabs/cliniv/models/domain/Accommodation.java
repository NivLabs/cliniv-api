package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.AccommodationType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.dto.AccommodationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "SALA_LEITO")
public class Accommodation extends BaseObjectWithId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String description;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SECTOR_ID")
    private Sector sector;

    public Accommodation(Long id, String description, AccommodationType type, Sector sector) {
        super();
        this.id = id;
        this.description = description;
        this.type = type;
        this.sector = sector;
    }

    public Accommodation() {
        super();
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

    public AccommodationType getType() {
        return type;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((sector == null) ? 0 : sector.hashCode());
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
        Accommodation other = (Accommodation) obj;
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
        if (sector == null) {
            if (other.sector != null)
                return false;
        } else if (!sector.equals(other.sector))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    public AccommodationDTO getDTO() {
        AccommodationDTO dto = new AccommodationDTO();
        BeanUtils.copyProperties(this, dto);
        dto.setSectorId(this.getSector().getId());
        return dto;

    }
}
