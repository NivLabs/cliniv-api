package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "TABELA_CBO")
public class CBOTable extends BaseObjectWithId<Long> {

    @Id
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    public CBOTable() {
        super();
    }

    public CBOTable(Long id, String description) {
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
    public String toString() {
        return "CBOTable{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CBOTable cboTable = (CBOTable) o;
        return Objects.equals(id, cboTable.id) && Objects.equals(description, cboTable.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
