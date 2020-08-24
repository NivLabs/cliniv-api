package br.com.nivlabs.gp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.enums.AccomodationType;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.dto.AccomodationDTO;

@Entity
@Table(name = "SALA_LEITO")
public class Accomodation extends BaseObjectWithId {

    private static final long serialVersionUID = 7743590060203121165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String description;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private AccomodationType type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SECTOR_ID")
    private Sector sector;

    public Accomodation(Long id, String description, AccomodationType type, Sector sector) {
        super();
        this.id = id;
        this.description = description;
        this.type = type;
        this.sector = sector;
    }

    public Accomodation() {
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

    public AccomodationType getType() {
        return type;
    }

    public void setType(AccomodationType type) {
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
        Accomodation other = (Accomodation) obj;
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

    public AccomodationDTO getDTO() {
        AccomodationDTO dto = new AccomodationDTO();
        BeanUtils.copyProperties(this, dto);
        dto.setSectorId(this.getSector().getId());
        return dto;

    }
}
