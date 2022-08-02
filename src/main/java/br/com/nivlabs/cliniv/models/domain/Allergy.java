package br.com.nivlabs.cliniv.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObject;

@Entity
@Table(name = "ALERGIA")
@IdClass(AllergyID.class)
public class Allergy extends BaseObject {

    private static final long serialVersionUID = 7282160459125369684L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Id
    @Column(name = "DESCRICAO")
    private String description;

    public Allergy() {
        super();
    }

    public Allergy(String description) {
        super();
        this.description = description;
    }

    public Allergy(Long id, String description) {
        super();
        this.id = id;
        this.description = description;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Allergy other = (Allergy) obj;
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
        return true;
    }

}
