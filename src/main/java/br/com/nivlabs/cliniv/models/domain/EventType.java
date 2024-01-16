package br.com.nivlabs.cliniv.models.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.dto.EventTypeDTO;

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
        StringBuilder builder = new StringBuilder();
        builder.append("EventType [id=");
        builder.append(id);
        builder.append(", superEventType=");
        builder.append(superEventType);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
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
