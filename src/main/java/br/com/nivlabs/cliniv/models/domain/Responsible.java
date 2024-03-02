package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Reponsible.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 7 de set de 2019
 */
@Entity
@Table(name = "RESPONSAVEL")
public class Responsible extends BaseObjectWithCreatedAt<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

    @Column(name = "REGISTRO_PROFISSIONAL")
    private String professionalIdentity;

    @Column(name = "SIGLA_ORGAO")
    private String initialsIdentity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "ESPECIALIDADE_RESPONSAVEL", joinColumns = @JoinColumn(name = "ID_RESPONSAVEL"), inverseJoinColumns = @JoinColumn(name = "ID_ESPECIALIDADE"))
    private List<Speciality> specializations = new ArrayList<>();

    public Responsible(Long id) {
        super();
        this.id = id;
    }

    public Responsible() {
        super();
    }

    public Responsible(Long id, Person person, String professionalIdentity, String initialsIdentity, List<Speciality> specializations) {
        super();
        this.id = id;
        this.person = person;
        this.professionalIdentity = professionalIdentity;
        this.initialsIdentity = initialsIdentity;
        this.specializations = specializations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getProfessionalIdentity() {
        return professionalIdentity;
    }

    public void setProfessionalIdentity(String professionalIdentity) {
        this.professionalIdentity = professionalIdentity;
    }

    public String getInitialsIdentity() {
        return initialsIdentity;
    }

    public void setInitialsIdentity(String initialsIdentity) {
        this.initialsIdentity = initialsIdentity;
    }

    public List<Speciality> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Speciality> specializations) {
        this.specializations = specializations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Responsible that = (Responsible) o;
        return Objects.equals(id, that.id) && Objects.equals(person, that.person) && Objects.equals(professionalIdentity, that.professionalIdentity) && Objects.equals(initialsIdentity, that.initialsIdentity) && Objects.equals(specializations, that.specializations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, professionalIdentity, initialsIdentity, specializations);
    }

    @Override
    public String toString() {
        return "Responsible{" +
                "id=" + id +
                ", person=" + person +
                ", professionalIdentity='" + professionalIdentity + '\'' +
                ", initialsIdentity='" + initialsIdentity + '\'' +
                ", specializations=" + specializations +
                '}';
    }

}
