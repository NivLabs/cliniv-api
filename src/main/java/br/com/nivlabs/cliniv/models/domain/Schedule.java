package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.nivlabs.cliniv.enums.ScheduleStatus;
import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;

/**
 * Entidade que representa um agendamento no banco
 * 
 * @author viniciosarodrigues
 *
 */
@Entity(name = "AGENDAMENTO")
public class Schedule extends BaseObjectWithCreatedAt {

    private static final long serialVersionUID = 4186684259235415032L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsible professional;

    @Column(name = "DATA_HORA")
    private LocalDateTime schedulingDateAndTime;

    @Column(name = "ANOTACOES")
    private String annotation;

    @Column(name = "SITUACAO")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Responsible getProfessional() {
        return professional;
    }

    public void setProfessional(Responsible professional) {
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

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }


    
    @Override
    public String toString() {
        return "Schedule [id=" + id + ", patient=" + patient + ", professional=" + professional + ", annotation=" + annotation + ", status="
                + status + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((annotation == null) ? 0 : annotation.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((patient == null) ? 0 : patient.hashCode());
        result = prime * result + ((professional == null) ? 0 : professional.hashCode());
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
        Schedule other = (Schedule) obj;
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
        if (status != other.status)
            return false;
        return true;
    }

}
