package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Informações do agendamento")
public class ScheduleInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -7082539967186369611L;

    @ApiModelProperty("Identificador único do agendamento")
    private Long id;

    @ApiModelProperty("Paciente do agendamento")
    private PatientDTO patient;

    @ApiModelProperty("Profissional do agendamento")
    private ResponsibleDTO professional;

    @ApiModelProperty("Data/Hora do agendamento")
    private LocalDateTime schedulingDateAndTime;

    @ApiModelProperty("Anotações sobre o agendamento")
    private String annotation;

    @ApiModelProperty("Verificador de confirmação de presença")
    private boolean isConfirmed;

    @ApiModelProperty("Verificador de paciente faltante")
    private boolean isMissed;

    @ApiModelProperty("Verificador de agendamento cancelado")
    private boolean isCanceled;

    public ScheduleInfoDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public ResponsibleDTO getProfessional() {
        return professional;
    }

    public void setProfessional(ResponsibleDTO professional) {
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

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public boolean isMissed() {
        return isMissed;
    }

    public void setMissed(boolean isMissed) {
        this.isMissed = isMissed;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Override
    public String toString() {
        return "ScheduleInfo [id=" + id + ", patient=" + patient + ", professional=" + professional + ", schedulingDateAndTime="
                + schedulingDateAndTime + ", annotation=" + annotation + ", isConfirmed=" + isConfirmed + ", isMissed=" + isMissed
                + ", isCanceled=" + isCanceled + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((annotation == null) ? 0 : annotation.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isCanceled ? 1231 : 1237);
        result = prime * result + (isConfirmed ? 1231 : 1237);
        result = prime * result + (isMissed ? 1231 : 1237);
        result = prime * result + ((patient == null) ? 0 : patient.hashCode());
        result = prime * result + ((professional == null) ? 0 : professional.hashCode());
        result = prime * result + ((schedulingDateAndTime == null) ? 0 : schedulingDateAndTime.hashCode());
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
        ScheduleInfoDTO other = (ScheduleInfoDTO) obj;
        if (annotation == null) {
            if (other.annotation != null)
                return false;
        } else if (!annotation.equals(other.annotation))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isCanceled != other.isCanceled)
            return false;
        if (isConfirmed != other.isConfirmed)
            return false;
        if (isMissed != other.isMissed)
            return false;
        if (patient == null) {
            if (other.patient != null)
                return false;
        } else if (!patient.equals(other.patient))
            return false;
        if (professional == null) {
            if (other.professional != null)
                return false;
        } else if (!professional.equals(other.professional))
            return false;
        if (schedulingDateAndTime == null) {
            if (other.schedulingDateAndTime != null)
                return false;
        } else if (!schedulingDateAndTime.equals(other.schedulingDateAndTime))
            return false;
        return true;
    }

}
