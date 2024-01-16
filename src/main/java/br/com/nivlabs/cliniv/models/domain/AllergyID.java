package br.com.nivlabs.cliniv.models.domain;

import java.io.Serializable;
import java.util.Objects;

public class AllergyID implements Serializable {

    private Long id;

    private String description;

    public AllergyID() {
        super();
    }

    public AllergyID(Long id, String description) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllergyID allergyID = (AllergyID) o;
        return Objects.equals(id, allergyID.id) && Objects.equals(description, allergyID.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "AllergyID{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
