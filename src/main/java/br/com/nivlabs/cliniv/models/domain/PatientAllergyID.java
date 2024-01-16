package br.com.nivlabs.cliniv.models.domain;

import java.io.Serializable;
import java.util.Objects;

public class PatientAllergyID implements Serializable {

    private Long patientId;

    private String description;

    public PatientAllergyID() {
        super();
    }

    public PatientAllergyID(Long patientId, String description) {
        super();
        this.patientId = patientId;
        this.description = description;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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
        PatientAllergyID that = (PatientAllergyID) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, description);
    }

    @Override
    public String toString() {
        return "PatientAllergyID{" +
                "patientId=" + patientId +
                ", description='" + description + '\'' +
                '}';
    }
}
