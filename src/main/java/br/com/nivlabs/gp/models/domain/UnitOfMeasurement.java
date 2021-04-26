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
    private String value;

    @Column(name = "DESCRICAO")
    private String description;

    public UnitOfMeasurement() {
        super();
    }

    public UnitOfMeasurement(Long id, String value, String description) {
        super();
        this.id = id;
        this.value = value;
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

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UnitOfMeasurement [id=");
        builder.append(id);
        builder.append(", value=");
        builder.append(value);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

}
