package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.dto.EventTypeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Classe EventType.java
 *
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "TIPO_EVENTO")
public class EventType extends BaseObjectWithId<Long> {

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

    @JsonIgnore
    public EventTypeDTO getEventTypeDTOFromDomain() {
        EventTypeDTO dtoEntity = new EventTypeDTO();

        dtoEntity.setId(getId());
        dtoEntity.setName(getName());
        dtoEntity.setDescription(getDescription());
        return dtoEntity;
    }

    @Override
    public String toString() {
        return "EventType{" +
                "id=" + id +
                ", superEventType=" + superEventType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
