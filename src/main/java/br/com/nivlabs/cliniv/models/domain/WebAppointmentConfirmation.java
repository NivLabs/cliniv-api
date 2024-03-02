package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "AGENDA_CONFIRMACAO")
public class WebAppointmentConfirmation implements Serializable {


    @Id
    @Column(name = "ID", length = 100)
    private String id;

    @Column(name = "DATA_HORA_RESP")
    private LocalDateTime dateTimeResponse;

    @Column(name = "IP_RESPOSTA", length = 45)
    private String originIp;

    @Column(name = "SITUACAO", length = 45)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "DATA_CRIACAO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsible responsible;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTimeResponse() {
        return dateTimeResponse;
    }

    public void setDateTimeResponse(LocalDateTime dateTimeResponse) {
        this.dateTimeResponse = dateTimeResponse;
    }

    public String getOriginIp() {
        return originIp;
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebAppointmentConfirmation that = (WebAppointmentConfirmation) o;
        return Objects.equals(id, that.id) && Objects.equals(dateTimeResponse, that.dateTimeResponse) && Objects.equals(originIp, that.originIp) && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(patient, that.patient) && Objects.equals(responsible, that.responsible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTimeResponse, originIp, status, createdAt, patient, responsible);
    }

    @Override
    public String toString() {
        return "WebAppointmentConfirmation{" +
                "id='" + id + '\'' +
                ", dateTimeResponse=" + dateTimeResponse +
                ", originIp='" + originIp + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", patient=" + patient +
                ", responsible=" + responsible +
                '}';
    }

}
