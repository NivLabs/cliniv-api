package br.com.nivlabs.gp.models.domain;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import br.com.nivlabs.gp.models.BaseObjectWithCreatedAt;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;

/**
 * Classe que representa os documentos digitais do prontuário
 * 
 * @author viniciosarodrigues
 *
 */
@Entity
@Table(name = "DOCUMENTO_DIGITAL")
public class DigitalDocument extends BaseObjectWithCreatedAt {

    private static final long serialVersionUID = 4392117344563755949L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA_EVENTO")
    private AttendanceEvent attendanceEvent;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private DigitalDocumentType type;

    @Column(name = "BASE64")
    @Lob
    private String base64;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime createdAt;

    public DigitalDocument() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendanceEvent getAttendanceEvent() {
        return attendanceEvent;
    }

    public void setAttendanceEvent(AttendanceEvent attendanceEvent) {
        this.attendanceEvent = attendanceEvent;
    }

    public DigitalDocumentType getType() {
        return type;
    }

    public void setType(DigitalDocumentType type) {
        this.type = type;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DigitalDocument [id=" + id + ", attendanceEvent=" + attendanceEvent + ", type=" + type + ", base64=" + base64 + ", name="
                + name + ", createdAt=" + createdAt + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attendanceEvent == null) ? 0 : attendanceEvent.hashCode());
        result = prime * result + ((base64 == null) ? 0 : base64.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        DigitalDocument other = (DigitalDocument) obj;
        if (attendanceEvent == null) {
            if (other.attendanceEvent != null)
                return false;
        } else if (!attendanceEvent.equals(other.attendanceEvent))
            return false;
        if (base64 == null) {
            if (other.base64 != null)
                return false;
        } else if (!base64.equals(other.base64))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    public DigitalDocumentDTO getDtoFromDomain() {
        DigitalDocumentDTO dto = new DigitalDocumentDTO();
        BeanUtils.copyProperties(this, dto);
        dto.setAttendanceEventId(attendanceEvent.getId());
        return dto;
    }

}
