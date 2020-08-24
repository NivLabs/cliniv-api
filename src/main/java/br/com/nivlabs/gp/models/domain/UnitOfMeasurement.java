package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "UNIDADE_MEDIDA")
public class UnitOfMeasurement extends BaseObjectWithId {

    private static final long serialVersionUID = -5469261685230682235L;

    @Id
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    public UnitOfMeasurement() {
        super();
    }

    public UnitOfMeasurement(Long id, String description) {
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
        UnitOfMeasurement other = (UnitOfMeasurement) obj;
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

    @Override
    public String toString() {
        return "UnitOfMeasurement [id=" + id + ", description=" + description + "]";
    }

}
