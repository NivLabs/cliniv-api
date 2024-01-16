package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.nivlabs.cliniv.ApplicationMain;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import br.com.nivlabs.cliniv.enums.AttendanceLevel;
import br.com.nivlabs.cliniv.enums.EntryType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;

/**
 * Classe Visit.java
 *
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "VISITA")
public class Attendance extends BaseObjectWithId {

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
        this.entryDateTime = LocalDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO));
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", patient=" + patient +
                ", entryDateTime=" + entryDateTime +
                ", exitDateTime=" + exitDateTime +
                ", currentAccommodation=" + currentAccommodation +
                ", professional=" + professional +
                ", entryType=" + entryType +
                ", events=" + events +
                ", evolutions=" + evolutions +
                ", reasonForEntry='" + reasonForEntry + '\'' +
                ", level=" + level +
                '}';
    }
}
