package br.com.nivlabs.gp.models.dto;

public class DashboardInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -3406499859427114990L;

    private long newPatients;
    private long medicalCareProvided;
    private long canceled;
    private long confirmed;

    public DashboardInfoDTO(long newPatients, long medicalCareProvided, long canceled, long confirmed) {
        super();
        this.newPatients = newPatients;
        this.medicalCareProvided = medicalCareProvided;
        this.canceled = canceled;
        this.confirmed = confirmed;
    }

    public long getNewPatients() {
        return newPatients;
    }

    public void setNewPatients(long newPatients) {
        this.newPatients = newPatients;
    }

    public long getMedicalCareProvided() {
        return medicalCareProvided;
    }

    public void setMedicalCareProvided(long medicalCareProvided) {
        this.medicalCareProvided = medicalCareProvided;
    }

    public long getCanceled() {
        return canceled;
    }

    public void setCanceled(long canceled) {
        this.canceled = canceled;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (canceled ^ (canceled >>> 32));
        result = prime * result + (int) (confirmed ^ (confirmed >>> 32));
        result = prime * result + (int) (medicalCareProvided ^ (medicalCareProvided >>> 32));
        result = prime * result + (int) (newPatients ^ (newPatients >>> 32));
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
        DashboardInfoDTO other = (DashboardInfoDTO) obj;
        if (canceled != other.canceled)
            return false;
        if (confirmed != other.confirmed)
            return false;
        if (medicalCareProvided != other.medicalCareProvided)
            return false;
        if (newPatients != other.newPatients)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DashboardInfoDTO [newPatients=" + newPatients + ", medicalCareProvided=" + medicalCareProvided + ", canceled=" + canceled
                + ", confirmed=" + confirmed + "]";
    }

}
