package br.com.nivlabs.cliniv.models.dto;

/**
 * 
 * Cards do Dashboard da aplicação
 *
 * @author viniciosarodrigues
 * @since 30-09-2021
 *
 */
public class DashboardCardsInformationDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -3406499859427114990L;

    private long newPatients;
    private long medicalCareProvided;
    private long canceled;
    private long confirmed;

    public DashboardCardsInformationDTO(long newPatients, long medicalCareProvided, long canceled, long confirmed) {
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DashboardCardsInformationDTO [newPatients=");
        builder.append(newPatients);
        builder.append(", medicalCareProvided=");
        builder.append(medicalCareProvided);
        builder.append(", canceled=");
        builder.append(canceled);
        builder.append(", confirmed=");
        builder.append(confirmed);
        builder.append("]");
        return builder.toString();
    }

}
