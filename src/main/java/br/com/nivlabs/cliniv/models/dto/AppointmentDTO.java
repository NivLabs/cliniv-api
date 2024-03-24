package br.com.nivlabs.cliniv.models.dto;

import br.com.nivlabs.cliniv.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Schema(description = "Informações resumidas do agendamento")
public class AppointmentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 3038808084714295686L;

    @Schema(description = "Identificador do agendamento")
    private Long id;

    @Schema(description = "Nome do paciente")
    private String patientName;

    @Schema(description = "CPF do paciente")
    private String patientCpf;

    @Schema(description = "Identificador único do profissional")
    private Long professionalId;

    @Schema(description = "Nome do profissional")
    private String professionalName;

    @Schema(description = "Data/Hora do agendamento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime schedulingDateAndTime;

    @Schema(description = "Situação atual do agendamento")
    private AppointmentStatus status = AppointmentStatus.WAITING_CONFIRMATION;

    public AppointmentDTO(Long id, String patientName, String patientCpf, Long professionalId, String professionalName,
                          LocalDateTime schedulingDateAndTime, AppointmentStatus status) {
        super();
        this.id = id;
        this.patientName = patientName;
        this.patientCpf = patientCpf;
        this.professionalId = professionalId;
        this.professionalName = professionalName;
        this.schedulingDateAndTime = schedulingDateAndTime;
        this.status = status;
    }

    public AppointmentDTO() {
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public String handlerApointmentInPatientReportReport() {
        return """
                ----------------------------------------------------------------------------------------------------------------------------------------------------------------------
                _{DATA_HORA} ------ _{PROFESSIONAL_NAME} ------ _{STATUS}
                """
                .replace("_{DATA_HORA}", schedulingDateAndTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .replace("_{PROFESSIONAL_NAME}", professionalName)
                .replace("_{STATUS}", status.getDescription());
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", patientCpf='" + patientCpf + '\'' +
                ", professionalId=" + professionalId +
                ", professionalName='" + professionalName + '\'' +
                ", schedulingDateAndTime=" + schedulingDateAndTime +
                ", status=" + status +
                '}';
    }
}
