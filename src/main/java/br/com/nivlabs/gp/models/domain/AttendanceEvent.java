package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.AttendanceEventDTO;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;

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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_EVENTO")
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
    private Accomodation accomodation;

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

    public Accomodation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(Accomodation accomodation) {
        this.accomodation = accomodation;
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
        return "AttendanceEvent [id=" + id + ", eventType=" + eventType + ", responsible=" + responsible + ", attendance=" + attendance
                + ", procedure=" + procedure + ", documents=" + documents + ", accomodation=" + accomodation + ", title=" + title
                + ", observations=" + observations + ", eventDateTime=" + eventDateTime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accomodation == null) ? 0 : accomodation.hashCode());
        result = prime * result + ((attendance == null) ? 0 : attendance.hashCode());
        result = prime * result + ((documents == null) ? 0 : documents.hashCode());
        result = prime * result + ((eventDateTime == null) ? 0 : eventDateTime.hashCode());
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((observations == null) ? 0 : observations.hashCode());
        result = prime * result + ((procedure == null) ? 0 : procedure.hashCode());
        result = prime * result + ((responsible == null) ? 0 : responsible.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        AttendanceEvent other = (AttendanceEvent) obj;
        if (accomodation == null) {
            if (other.accomodation != null)
                return false;
        } else if (!accomodation.equals(other.accomodation))
            return false;
        if (attendance == null) {
            if (other.attendance != null)
                return false;
        } else if (!attendance.equals(other.attendance))
            return false;
        if (documents == null) {
            if (other.documents != null)
                return false;
        } else if (!documents.equals(other.documents))
            return false;
        if (eventDateTime == null) {
            if (other.eventDateTime != null)
                return false;
        } else if (!eventDateTime.equals(other.eventDateTime))
            return false;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (observations == null) {
            if (other.observations != null)
                return false;
        } else if (!observations.equals(other.observations))
            return false;
        if (procedure == null) {
            if (other.procedure != null)
                return false;
        } else if (!procedure.equals(other.procedure))
            return false;
        if (responsible == null) {
            if (other.responsible != null)
                return false;
        } else if (!responsible.equals(other.responsible))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
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

    public AttendanceEventDTO getDTO() {
        return new AttendanceEventDTO(this.getId(),
                this.getEventDateTime(), this.getEventType().getDescription(), convertDocuments(this.getDocuments()),
                this.getAccomodation().getDTO());
    }

    private List<DigitalDocumentDTO> convertDocuments(List<DigitalDocument> documents) {
        List<DigitalDocumentDTO> returnList = new ArrayList<>();
        documents.forEach(doc -> {
            DigitalDocumentDTO docToList = new DigitalDocumentDTO();
            BeanUtils.copyProperties(doc, docToList, "base64");
            returnList.add(docToList);
        });
        return returnList;
    }

}
