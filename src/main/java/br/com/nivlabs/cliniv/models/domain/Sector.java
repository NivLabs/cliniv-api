package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Sector.java
 *
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * @since 13 dez, 2019
 */
@Entity
@Table(name = "SETOR")
public class Sector extends BaseObjectWithId<Long> {


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(id, sector.id) && Objects.equals(description, sector.description) && Objects.equals(createdAt, sector.createdAt) && Objects.equals(listOfRoomsOrBeds, sector.listOfRoomsOrBeds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createdAt, listOfRoomsOrBeds);
    }

    @Override
    public String toString() {
        return "Sector{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", listOfRoomsOrBeds=" + listOfRoomsOrBeds +
                '}';
    }

}
