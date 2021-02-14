package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.ScheduleStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Informações resumidas do agendamento")
public class ScheduleDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 3038808084714295686L;

    @ApiModelProperty("Identificador do agendamento")
    private Long id;

    @ApiModelProperty("Nome do paciente")
    private String patientName;

    @ApiModelProperty("CPF do paciente")
    private String patientCpf;

    @ApiModelProperty("Identificador único do profissional")
    private Long professionalId;

    @ApiModelProperty("Nome do profissional")
    private String professionalName;

    @ApiModelProperty("Data/Hora do agendamento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime schedulingDateAndTime;

    @ApiModelProperty("Situação atual do agendamento")
    private ScheduleStatus status = ScheduleStatus.WAITING_CONFIRMATION;

    public ScheduleDTO(Long id, String patientName, String patientCpf, Long professionalId, String professionalName,
            LocalDateTime schedulingDateAndTime, ScheduleStatus status) {
        super();
        this.id = id;
        this.patientName = patientName;
        this.patientCpf = patientCpf;
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.schedulingDateAndTime = schedulingDateAndTime;
        this.status = status;
    }

    public ScheduleDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientCpf() {
        return patientCpf;
    }

    public void setPatientCpf(String patientCpf) {
        this.patientCpf = patientCpf;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public LocalDateTime getSchedulingDateAndTime() {
        return schedulingDateAndTime;
    }

    public void setSchedulingDateAndTime(LocalDateTime schedulingDateAndTime) {
        this.schedulingDateAndTime = schedulingDateAndTime;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScheduleDTO [id=" + id + ", patientName=" + patientName + ", patientCpf=" + patientCpf + ", professionalId="
                + professionalId + ", professionalName=" + professionalName + ", schedulingDateAndTime=" + schedulingDateAndTime
                + ", status=" + status + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((patientCpf == null) ? 0 : patientCpf.hashCode());
        result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
        result = prime * result + ((professionalId == null) ? 0 : professionalId.hashCode());
        result = prime * result + ((professionalName == null) ? 0 : professionalName.hashCode());
        result = prime * result + ((schedulingDateAndTime == null) ? 0 : schedulingDateAndTime.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
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
        ScheduleDTO other = (ScheduleDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (patientCpf == null) {
            if (other.patientCpf != null)
                return false;
        } else if (!patientCpf.equals(other.patientCpf))
            return false;
        if (patientName == null) {
            if (other.patientName != null)
                return false;
        } else if (!patientName.equals(other.patientName))
            return false;
        if (professionalId == null) {
            if (other.professionalId != null)
                return false;
        } else if (!professionalId.equals(other.professionalId))
            return false;
        if (professionalName == null) {
            if (other.professionalName != null)
                return false;
        } else if (!professionalName.equals(other.professionalName))
            return false;
        if (schedulingDateAndTime == null) {
            if (other.schedulingDateAndTime != null)
                return false;
        } else if (!schedulingDateAndTime.equals(other.schedulingDateAndTime))
            return false;
        if (status != other.status)
            return false;
        return true;
    }

}
