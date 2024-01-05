package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.cliniv.enums.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações do agendamento")
public class AppointmentInfoDTO extends DataTransferObjectBase {


    @Schema(description = "Identificador único do agendamento")
    private Long id;

    @Schema(description = "Paciente do agendamento")
    private PatientInfoDTO patient;

    @Schema(description = "Profissional do agendamento")
    private ResponsibleInfoDTO professional;

    @Schema(description = "Data agendada")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime schedulingDateAndTime;

    @Schema(description = "Datado agendamento")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @Schema(description = "Anotações sobre o agendamento")
    private String annotation;

    @Schema(description = "Situação atual do agendamento")
    private AppointmentStatus status = AppointmentStatus.WAITING_CONFIRMATION;

    private AppointmentRecurrenceSettingsDTO repeatSettings;

    public AppointmentInfoDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientInfoDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientInfoDTO patient) {
        this.patient = patient;
    }

    public ResponsibleInfoDTO getProfessional() {
        return professional;
    }

    public void setProfessional(ResponsibleInfoDTO professional) {
        this.professional = professional;
    }

    public LocalDateTime getSchedulingDateAndTime() {
        return schedulingDateAndTime;
    }

    public void setSchedulingDateAndTime(LocalDateTime schedulingDateAndTime) {
        this.schedulingDateAndTime = schedulingDateAndTime;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AppointmentRecurrenceSettingsDTO getRepeatSettings() {
        return repeatSettings;
    }

    public void setRepeatSettings(AppointmentRecurrenceSettingsDTO repeatSettings) {
        this.repeatSettings = repeatSettings;
    }

    @Override
    public String toString() {
        return "AppointmentInfoDTO{" +
                "id=" + id +
                ", patient=" + patient +
                ", professional=" + professional +
                ", schedulingDateAndTime=" + schedulingDateAndTime +
                ", createdAt=" + createdAt +
                ", annotation='" + annotation + '\'' +
                ", status=" + status +
                ", repeatSettings=" + repeatSettings +
                '}';
    }
}
