package br.com.nivlabs.gp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.EventTypeDTO;

/**
 * Classe EventType.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "TIPO_EVENTO")
public class EventType extends BaseObjectWithId {

    private static final long serialVersionUID = -8716334303463572525L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    private EventType superEventType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "PROCEDIMENTO_EQ_ID")
    private Procedure procedure;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    public EventType() {
        super();
    }

    public EventType(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getSuperEventType() {
        return superEventType;
    }

    public void setSuperEventType(EventType superEventType) {
        this.superEventType = superEventType;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((procedure == null) ? 0 : procedure.hashCode());
        result = prime * result + ((superEventType == null) ? 0 : superEventType.hashCode());
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
        EventType other = (EventType) obj;
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
        if (procedure == null) {
            if (other.procedure != null)
                return false;
        } else if (!procedure.equals(other.procedure))
            return false;
        if (superEventType == null) {
            if (other.superEventType != null)
                return false;
        } else if (!superEventType.equals(other.superEventType))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EventType [id=" + id + ", superEventType=" + superEventType + ", procedure=" + procedure + ", name=" + name
                + ", description=" + description + "]";
    }

    @JsonIgnore
    public EventTypeDTO getEventTypeDTOFromDomain() {
        EventTypeDTO dtoEntity = new EventTypeDTO();

        dtoEntity.setId(getId());
        dtoEntity.setName(getName());
        dtoEntity.setDescription(getDescription());
        return dtoEntity;
    }

}
