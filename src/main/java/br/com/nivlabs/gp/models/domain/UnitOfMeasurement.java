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

    @Column(name = "TERMO")
    private String abbreviation;

    @Column(name = "DESCRICAO")
    private String description;

    public UnitOfMeasurement() {
        super();
    }

    public UnitOfMeasurement(Long id, String abbreviation, String description) {
        super();
        this.id = id;
        this.abbreviation = abbreviation;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UnitOfMeasurement ["
                + "id=" + id
                + ", abbreviation=" + abbreviation
                + ", description=" + description
                + "]";
    }

}
