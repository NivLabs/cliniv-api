package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;

/**
 * Classe VisitEvent.java
 * 
 * @version 1.0
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 * @since 08 Sept, 2019
 * 
 * @version 2.0
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 15 Dez, 2019
 */
@Entity
@Table(name = "VISITA_EVENTO")
public class AttendanceEvent extends BaseObjectWithId {

    private static final long serialVersionUID = 8988537898462013276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIPO_EVENTO")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsible responsible;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROCEDIMENTO")
    private Procedure procedure;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "attendanceEvent")
    private List<DigitalDocument> documents = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SALA_LEITO")
    private Accommodation accommodation;

    @Column(name = "TITULO")
    private String title;

    @Column(name = "OBSERVACOES")
    private String observations;

    @Column(name = "DH_EVENTO")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime eventDateTime;

    public AttendanceEvent() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public List<DigitalDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DigitalDocument> documents) {
        this.documents = documents;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AttendanceEvent [id=");
        builder.append(id);
        builder.append(", eventType=");
        builder.append(eventType);
        builder.append(", responsible=");
        builder.append(responsible);
        builder.append(", attendance=");
        builder.append(attendance);
        builder.append(", procedure=");
        builder.append(procedure);
        builder.append(", documents=");
        builder.append(documents);
        builder.append(", accommodation=");
        builder.append(accommodation);
        builder.append(", title=");
        builder.append(title);
        builder.append(", observations=");
        builder.append(observations);
        builder.append(", eventDateTime=");
        builder.append(eventDateTime);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(accommodation, attendance, documents, eventDateTime, eventType, id, observations, procedure, responsible,
                            title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttendanceEvent other = (AttendanceEvent) obj;
        return Objects.equals(accommodation, other.accommodation) && Objects.equals(attendance, other.attendance)
                && Objects.equals(documents, other.documents) && Objects.equals(eventDateTime, other.eventDateTime)
                && eventType == other.eventType && Objects.equals(id, other.id) && Objects.equals(observations, other.observations)
                && Objects.equals(procedure, other.procedure) && Objects.equals(responsible, other.responsible)
                && Objects.equals(title, other.title);
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.eventDateTime = now;
    }

    public AttendanceEvent(Long id) {
        super();
        this.id = id;
    }

}
