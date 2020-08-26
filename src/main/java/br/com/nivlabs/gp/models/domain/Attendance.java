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
    private LocalDateTime dateTimeEntry;

    @Column(name = "DH_SAIDA")
    private LocalDateTime dateTimeExit;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "SETOR_ATUAL")
    private Sector currentSector;

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

    public LocalDateTime getDateTimeEntry() {
        return dateTimeEntry;
    }

    public void setDateTimeEntry(LocalDateTime dateTimeEntry) {
        this.dateTimeEntry = dateTimeEntry;
    }

    public LocalDateTime getDateTimeExit() {
        return dateTimeExit;
    }

    public void setDateTimeExit(LocalDateTime dateTimeExit) {
        this.dateTimeExit = dateTimeExit;
    }

    public Sector getCurrentSector() {
        return currentSector;
    }

    public void setCurrentSector(Sector currentSector) {
        this.currentSector = currentSector;
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

    @Override
    public String toString() {
        return "Attendance [id=" + id + ", patient=" + patient + ", dateTimeEntry=" + dateTimeEntry + ", dateTimeExit=" + dateTimeExit
                + ", currentSector=" + currentSector + ", entryType=" + entryType + ", events=" + events + ", evolutions=" + evolutions
                + ", reasonForEntry=" + reasonForEntry + ", level=" + level + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currentSector == null) ? 0 : currentSector.hashCode());
        result = prime * result + ((dateTimeEntry == null) ? 0 : dateTimeEntry.hashCode());
        result = prime * result + ((dateTimeExit == null) ? 0 : dateTimeExit.hashCode());
        result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((evolutions == null) ? 0 : evolutions.hashCode());
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
        if (currentSector == null) {
            if (other.currentSector != null)
                return false;
        } else if (!currentSector.equals(other.currentSector))
            return false;
        if (dateTimeEntry == null) {
            if (other.dateTimeEntry != null)
                return false;
        } else if (!dateTimeEntry.equals(other.dateTimeEntry))
            return false;
        if (dateTimeExit == null) {
            if (other.dateTimeExit != null)
                return false;
        } else if (!dateTimeExit.equals(other.dateTimeExit))
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

    public AttendanceLevel getLevel() {
        return level;
    }

    public void setLevel(AttendanceLevel level) {
        this.level = level;
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.dateTimeEntry = now;
    }

}
