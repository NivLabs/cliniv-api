package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import br.com.nivlabs.gp.enums.AttendanceLevel;
import br.com.nivlabs.gp.enums.EntryType;
import br.com.nivlabs.gp.models.BaseObjectWithId;

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
    private Accomodation currentAccomodation;

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

    public Accomodation getCurrentAccomodation() {
        return currentAccomodation;
    }

    public void setCurrentAccomodation(Accomodation currentAccomodation) {
        this.currentAccomodation = currentAccomodation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currentAccomodation == null) ? 0 : currentAccomodation.hashCode());
        result = prime * result + ((entryDateTime == null) ? 0 : entryDateTime.hashCode());
        result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((evolutions == null) ? 0 : evolutions.hashCode());
        result = prime * result + ((exitDateTime == null) ? 0 : exitDateTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((patient == null) ? 0 : patient.hashCode());
        result = prime * result + ((reasonForEntry == null) ? 0 : reasonForEntry.hashCode());
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
        Attendance other = (Attendance) obj;
        if (currentAccomodation == null) {
            if (other.currentAccomodation != null)
                return false;
        } else if (!currentAccomodation.equals(other.currentAccomodation))
            return false;
        if (entryDateTime == null) {
            if (other.entryDateTime != null)
                return false;
        } else if (!entryDateTime.equals(other.entryDateTime))
            return false;
        if (entryType != other.entryType)
            return false;
        if (events == null) {
            if (other.events != null)
                return false;
        } else if (!events.equals(other.events))
            return false;
        if (evolutions == null) {
            if (other.evolutions != null)
                return false;
        } else if (!evolutions.equals(other.evolutions))
            return false;
        if (exitDateTime == null) {
            if (other.exitDateTime != null)
                return false;
        } else if (!exitDateTime.equals(other.exitDateTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (level != other.level)
            return false;
        if (patient == null) {
            if (other.patient != null)
                return false;
        } else if (!patient.equals(other.patient))
            return false;
        if (reasonForEntry == null) {
            if (other.reasonForEntry != null)
                return false;
        } else if (!reasonForEntry.equals(other.reasonForEntry))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Attendance [id=" + id + ", patient=" + patient + ", entryDateTime=" + entryDateTime + ", exitDateTime=" + exitDateTime
                + ", currentAccomodation=" + currentAccomodation + ", entryType=" + entryType + ", events=" + events + ", evolutions="
                + evolutions + ", reasonForEntry=" + reasonForEntry + ", level=" + level + "]";
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.entryDateTime = now;
    }
}
