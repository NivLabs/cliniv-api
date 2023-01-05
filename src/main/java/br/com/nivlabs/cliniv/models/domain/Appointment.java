package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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
import javax.persistence.Table;

import br.com.nivlabs.cliniv.enums.AppointmentStatus;
import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;

/**
 * Entidade que representa um agendamento no banco
 * 
 * @author viniciosarodrigues
 *
 */
@Table
@Entity(name = "AGENDAMENTO")
public class Appointment extends BaseObjectWithCreatedAt {

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
    private LocalDateTime appointmentDateAndTime;

    @Column(name = "ANOTACOES")
    private String annotation;

    @Column(name = "SITUACAO")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

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

    public LocalDateTime getAppointmentDateAndTime() {
        return appointmentDateAndTime;
    }

    public void setAppointmentDateAndTime(LocalDateTime appointmentDateAndTime) {
        this.appointmentDateAndTime = appointmentDateAndTime;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Appointment [id=");
        builder.append(id);
        builder.append(", patient=");
        builder.append(patient);
        builder.append(", professional=");
        builder.append(professional);
        builder.append(", appointmentDateAndTime=");
        builder.append(appointmentDateAndTime);
        builder.append(", annotation=");
        builder.append(annotation);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, appointmentDateAndTime, id, patient, professional, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Appointment other = (Appointment) obj;
        return Objects.equals(annotation, other.annotation) && Objects.equals(appointmentDateAndTime, other.appointmentDateAndTime)
                && Objects.equals(id, other.id) && Objects.equals(patient, other.patient)
                && Objects.equals(professional, other.professional) && status == other.status;
    }

}
