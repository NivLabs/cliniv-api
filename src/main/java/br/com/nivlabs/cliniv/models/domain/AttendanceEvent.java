package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.enums.EventType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.domain.tiss.Procedure;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Classe VisitEvent.java
 *
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @version 2.0
 * @since 08 Sept, 2019
 * @since 15 Dez, 2019
 */
@Entity
@Table(name = "VISITA_EVENTO")
public class AttendanceEvent extends BaseObjectWithId<Long> {

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
    @Lob
    private byte[] observations;

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
        return observations != null ? new String(Base64.getDecoder().decode(observations), StandardCharsets.UTF_8) : null;
    }

    public void setObservations(String observations) {
        this.observations = observations != null ? Base64.getEncoder().encode(observations.getBytes()) : null;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceEvent that = (AttendanceEvent) o;
        return Objects.equals(id, that.id) && eventType == that.eventType && Objects.equals(responsible, that.responsible) && Objects.equals(attendance, that.attendance) && Objects.equals(procedure, that.procedure) && Objects.equals(documents, that.documents) && Objects.equals(accommodation, that.accommodation) && Objects.equals(title, that.title) && Arrays.equals(observations, that.observations) && Objects.equals(eventDateTime, that.eventDateTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, eventType, responsible, attendance, procedure, documents, accommodation, title, eventDateTime);
        result = 31 * result + Arrays.hashCode(observations);
        return result;
    }

    @PrePersist
    public void prePersist() {
        this.eventDateTime = LocalDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO));
    }

    public AttendanceEvent(Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return "AttendanceEvent{" +
                "id=" + id +
                ", eventType=" + eventType +
                ", responsible=" + responsible +
                ", attendance=" + attendance +
                ", procedure=" + procedure +
                ", documents=" + documents +
                ", accommodation=" + accommodation +
                ", title='" + title + '\'' +
                ", observations=" + Arrays.toString(observations) +
                ", eventDateTime=" + eventDateTime +
                '}';
    }
}
