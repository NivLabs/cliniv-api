package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Specialty.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 23 de set de 2019
 */
@Entity
@Table(name = "ESPECIALIDADE")
public class Speciality extends BaseObjectWithId<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "ESPECIALIDADE_RESPONSAVEL", joinColumns = @JoinColumn(name = "ID_ESPECIALIDADE"), inverseJoinColumns = @JoinColumn(name = "ID_RESPONSAVEL"))
    private List<Responsible> responsibles = new ArrayList<>();

    public Speciality() {
        super();
    }

    public Speciality(Long id, String name, String description, List<Responsible> responsibles) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.responsibles = responsibles;
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

    public List<Responsible> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(List<Responsible> responsibles) {
        this.responsibles = responsibles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speciality that = (Speciality) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(responsibles, that.responsibles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, responsibles);
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", responsibles=" + responsibles +
                '}';
    }

}
