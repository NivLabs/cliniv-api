package br.com.nivlabs.cliniv.models.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.nivlabs.cliniv.enums.ScheduleStatus;

@Table(name = "AGENDA_CONFIRMACAO")
public class WebScheduleConfirmation implements Serializable {

    private static final long serialVersionUID = 3844020943998908507L;

    @Id
    @Column(name = "ID", length = 100)
    private String id;

    @Column(name = "DATA_HORA_RESP")
    private LocalDateTime dateTimeResponse;

    @Column(name = "IP_RESPOSTA", length = 45)
    private String originIp;

    @Column(name = "SITUACAO", length = 45)
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "DATA_CRIACAO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_RESPONSVEL")
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

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((dateTimeResponse == null) ? 0 : dateTimeResponse.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((originIp == null) ? 0 : originIp.hashCode());
        result = prime * result + ((patient == null) ? 0 : patient.hashCode());
        result = prime * result + ((responsible == null) ? 0 : responsible.hashCode());
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
        WebScheduleConfirmation other = (WebScheduleConfirmation) obj;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (dateTimeResponse == null) {
            if (other.dateTimeResponse != null)
                return false;
        } else if (!dateTimeResponse.equals(other.dateTimeResponse))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (originIp == null) {
            if (other.originIp != null)
                return false;
        } else if (!originIp.equals(other.originIp))
            return false;
        if (patient == null) {
            if (other.patient != null)
                return false;
        } else if (!patient.equals(other.patient))
            return false;
        if (responsible == null) {
            if (other.responsible != null)
                return false;
        } else if (!responsible.equals(other.responsible))
            return false;
        if (status != other.status)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WebScheduleConfirmation [id=" + id + ", dateTimeResponse=" + dateTimeResponse + ", originIp=" + originIp + ", status="
                + status + ", createdAt=" + createdAt + ", patient=" + patient + ", responsible=" + responsible + "]";
    }

}
