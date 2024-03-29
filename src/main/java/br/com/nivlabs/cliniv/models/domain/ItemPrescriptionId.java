package br.com.nivlabs.cliniv.models.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;

@Embeddable
public class ItemPrescriptionId implements Serializable {

    @Column(name = "SEQUENCIAL", insertable = false, updatable = false)
    private Long sequential;

    @JoinColumn(name = "ID_PRESCRICAO", insertable = false, updatable = false)
    private Long prescriptionId;

    public ItemPrescriptionId() {
        super();
    }

    public ItemPrescriptionId(Long sequential, Long prescriptionId) {
        super();
        this.sequential = sequential;
        this.prescriptionId = prescriptionId;
    }

    public Long getSequential() {
        return sequential;
    }

    public void setSequential(Long sequential) {
        this.sequential = sequential;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((prescriptionId == null) ? 0 : prescriptionId.hashCode());
        result = prime * result + ((sequential == null) ? 0 : sequential.hashCode());
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
        ItemPrescriptionId other = (ItemPrescriptionId) obj;
        if (prescriptionId == null) {
            if (other.prescriptionId != null)
                return false;
        } else if (!prescriptionId.equals(other.prescriptionId))
            return false;
        if (sequential == null) {
            if (other.sequential != null)
                return false;
        } else if (!sequential.equals(other.sequential))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ItemPrescriptionIdPK [sequential=" + sequential + ", prescriptionId=" + prescriptionId + "]";
    }

}
