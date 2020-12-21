package br.com.nivlabs.gp.models.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithCreatedAt;

/**
 * Classe Reponsible.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Entity
@Table(name = "RESPONSAVEL")
public class Responsible extends BaseObjectWithCreatedAt {

    private static final long serialVersionUID = 6649135435429790655L;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((initialsIdentity == null) ? 0 : initialsIdentity.hashCode());
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        result = prime * result + ((professionalIdentity == null) ? 0 : professionalIdentity.hashCode());
        result = prime * result + ((specializations == null) ? 0 : specializations.hashCode());
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
        Responsible other = (Responsible) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (initialsIdentity == null) {
            if (other.initialsIdentity != null)
                return false;
        } else if (!initialsIdentity.equals(other.initialsIdentity))
            return false;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        if (professionalIdentity == null) {
            if (other.professionalIdentity != null)
                return false;
        } else if (!professionalIdentity.equals(other.professionalIdentity))
            return false;
        if (specializations == null) {
            if (other.specializations != null)
                return false;
        } else if (!specializations.equals(other.specializations))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Responsible [id=" + id + ", person=" + person + ", professionalIdentity=" + professionalIdentity + ", initialsIdentity="
                + initialsIdentity + ", specializations=" + specializations + "]";
    }

}
