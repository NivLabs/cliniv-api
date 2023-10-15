package br.com.nivlabs.cliniv.models.dto;

import java.util.ArrayList;
import java.util.List;

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

    // Cards superiores relacionados às informações da unidade
    private long newPatients;
    private long medicalCareProvided;
    private long canceled;
    private long confirmed;

    // Cards inferiores relacionados ao profissional e usuário
    private List<AppointmentDTO> appointments = new ArrayList<>();

    private long totalActiveAttendance;
    private long totalAttendanceProvided;
    private long totalUnconfirmedAppointment;

    private List<StickerDTO> stickers = new ArrayList<>();

    public DashboardCardsInformationDTO() {
        super();
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

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public long getTotalActiveAttendance() {
        return totalActiveAttendance;
    }

    public void setTotalActiveAttendance(long totalActiveAttendance) {
        this.totalActiveAttendance = totalActiveAttendance;
    }

    public long getTotalAttendanceProvided() {
        return totalAttendanceProvided;
    }

    public void setTotalAttendanceProvided(long totalAttendanceProvided) {
        this.totalAttendanceProvided = totalAttendanceProvided;
    }

    public long getTotalUnconfirmedAppointment() {
        return totalUnconfirmedAppointment;
    }

    public void setTotalUnconfirmedAppointment(long totalUnconfirmedAppointment) {
        this.totalUnconfirmedAppointment = totalUnconfirmedAppointment;
    }

    public List<StickerDTO> getStickers() {
        return stickers;
    }

    public void setStickers(List<StickerDTO> stickers) {
        this.stickers = stickers;
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
        builder.append(", appointments=");
        builder.append(appointments);
        builder.append(", totalActiveAttendance=");
        builder.append(totalActiveAttendance);
        builder.append(", totalAttendanceProvided=");
        builder.append(totalAttendanceProvided);
        builder.append(", totalUnconfirmedAppointment=");
        builder.append(totalUnconfirmedAppointment);
        builder.append(", stickers=");
        builder.append(stickers);
        builder.append("]");
        return builder.toString();
    }

}
