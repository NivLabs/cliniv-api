package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "UNIDADE_MEDIDA")
public class UnitOfMeasurement extends BaseObjectWithId<Long> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitOfMeasurement that = (UnitOfMeasurement) o;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, description);
    }

    @Override
    public String toString() {
        return "UnitOfMeasurement{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
