package br.com.nivlabs.gp.models.domain;

import java.io.Serializable;

public class PatientAllergyID implements Serializable {

    private static final long serialVersionUID = -7039331290753025629L;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
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
        PatientAllergyID other = (PatientAllergyID) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (patientId == null) {
            if (other.patientId != null)
                return false;
        } else if (!patientId.equals(other.patientId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PatientAllergyID [patientId=" + patientId + ", description=" + description + "]";
    }

}
