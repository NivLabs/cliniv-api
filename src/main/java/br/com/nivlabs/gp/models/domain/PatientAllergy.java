package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObject;

@Entity
@Table(name = "PACIENTE_ALERGIA")
@IdClass(PatientAllergyID.class)
public class PatientAllergy extends BaseObject {

    private static final long serialVersionUID = -727231950405483043L;

    @Id
    @Column(name = "ID_PACIENTE")
    private Long patientId;

    @Id
    @Column(name = "DESCRICAO")
    private String description;

    public PatientAllergy() {
        super();
    }

    public PatientAllergy(Long patientId, String description) {
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
        PatientAllergy other = (PatientAllergy) obj;
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
        return "PatientAllergy [patientId=" + patientId + ", description=" + description + "]";
    }

}
