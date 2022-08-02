package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;

/**
 * Classe Sector.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 dez, 2019
 */
@Entity
@Table(name = "SETOR")
public class Sector extends BaseObjectWithId {

    private static final long serialVersionUID = -8491049323618565782L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "DATA_CRIACAO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "sector", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Accommodation> listOfRoomsOrBeds = new ArrayList<>();

    public Sector(Long id) {
        this.id = id;
    }

    public Sector() {
        super();
    }

    public Sector(Long id, String description, LocalDateTime createdAt, List<Accommodation> listOfRoomsOrBeds) {
        super();
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.listOfRoomsOrBeds = listOfRoomsOrBeds;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Accommodation> getListOfRoomsOrBeds() {
        return listOfRoomsOrBeds;
    }

    public void setListOfRoomsOrBeds(List<Accommodation> listOfRoomsOrBeds) {
        this.listOfRoomsOrBeds = listOfRoomsOrBeds;
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
        Sector other = (Sector) obj;
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
        return "Sector [id=" + id + ", description=" + description + ", createdAt=" + createdAt + ", listOfRoomsOrBeds=" + listOfRoomsOrBeds
                + "]";
    }

}
