package br.com.nivlabs.cliniv.models.domain;

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

import br.com.nivlabs.cliniv.enums.AttendanceLevel;
import br.com.nivlabs.cliniv.enums.EntryType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;

/**
 * Classe Visit.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "VISITA")
public class Attendance extends BaseObjectWithId {

    private static final long serialVersionUID = -2728953699232281599L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @Column(name = "DH_ENTRADA")
    private LocalDateTime entryDateTime;

    @Column(name = "DH_SAIDA")
    private LocalDateTime exitDateTime;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACOMODACAO_ATUAL")
    private Accommodation currentAccommodation;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFISSIONAL_ATUAL")
    private Responsible professional;

    @Column(name = "TIPO_ENTRADA")
    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "attendance")
    private List<AttendanceEvent> events = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "attendance")
    private List<Evolution> evolutions = new ArrayList<>();

    @Column(name = "MOTIVO_ENTRADA")
    private String reasonForEntry;

    @Column(name = "NIVEL_RISCO")
    @Enumerated(EnumType.STRING)
    private AttendanceLevel level;

    public Attendance() {
        super();
    }

    public Attendance(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Responsible getProfessional() {
        return professional;
    }

    public void setProfessional(Responsible professional) {
        this.professional = professional;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public LocalDateTime getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(LocalDateTime exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public List<AttendanceEvent> getEvents() {
        return events;
    }

    public void setEvents(List<AttendanceEvent> events) {
        this.events = events;
    }

    public List<Evolution> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<Evolution> evolutions) {
        this.evolutions = evolutions;
    }

    public String getReasonForEntry() {
        return reasonForEntry;
    }

    public void setReasonForEntry(String reasonForEntry) {
        this.reasonForEntry = reasonForEntry;
    }

    public AttendanceLevel getLevel() {
        return level;
    }

    public void setLevel(AttendanceLevel level) {
        this.level = level;
    }

    public Accommodation getCurrentAccommodation() {
        return currentAccommodation;
    }

    public void setCurrentAccommodation(Accommodation currentAccommodation) {
        this.currentAccommodation = currentAccommodation;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Attendance [id=");
        builder.append(id);
        builder.append(", patient=");
        builder.append(patient);
        builder.append(", entryDateTime=");
        builder.append(entryDateTime);
        builder.append(", exitDateTime=");
        builder.append(exitDateTime);
        builder.append(", currentAccommodation=");
        builder.append(currentAccommodation);
        builder.append(", professional=");
        builder.append(professional);
        builder.append(", entryType=");
        builder.append(entryType);
        builder.append(", events=");
        builder.append(events);
        builder.append(", evolutions=");
        builder.append(evolutions);
        builder.append(", reasonForEntry=");
        builder.append(reasonForEntry);
        builder.append(", level=");
        builder.append(level);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentAccommodation, entryDateTime, entryType, events, evolutions, exitDateTime, id, level, patient,
                            professional, reasonForEntry);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Attendance other = (Attendance) obj;
        return Objects.equals(currentAccommodation, other.currentAccommodation) && Objects.equals(entryDateTime, other.entryDateTime)
                && entryType == other.entryType && Objects.equals(events, other.events) && Objects.equals(evolutions, other.evolutions)
                && Objects.equals(exitDateTime, other.exitDateTime) && Objects.equals(id, other.id) && level == other.level
                && Objects.equals(patient, other.patient) && Objects.equals(professional, other.professional)
                && Objects.equals(reasonForEntry, other.reasonForEntry);
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.entryDateTime = now;
    }
}
