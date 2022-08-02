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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.enums.DigitalDocumentType;
import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;

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
    public int hashCode() {
        return Objects.hash(attendanceEvent, base64, createdAt, id, name, type);
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
        return Objects.equals(attendanceEvent, other.attendanceEvent) && Objects.equals(base64, other.base64)
                && Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && type == other.type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DigitalDocument [id=");
        builder.append(id);
        builder.append(", attendanceEvent=");
        builder.append(attendanceEvent);
        builder.append(", type=");
        builder.append(type);
        builder.append(", base64=");
        builder.append(base64);
        builder.append(", name=");
        builder.append(name);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append("]");
        return builder.toString();
    }
}
