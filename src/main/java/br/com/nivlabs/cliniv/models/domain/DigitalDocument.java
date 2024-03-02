package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.DigitalDocumentType;
import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Classe que representa os documentos digitais do prontuário
 *
 * @author viniciosarodrigues
 */
@Entity
@Table(name = "DOCUMENTO_DIGITAL")
public class DigitalDocument extends BaseObjectWithCreatedAt<Long> {

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
    private byte[] base64;

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
        return base64 != null ? new String(base64, StandardCharsets.UTF_8) : null;
    }

    public void setBase64(String base64) {
        this.base64 = base64 != null ? base64.getBytes() : null;
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
        return Objects.hash(attendanceEvent, Arrays.hashCode(base64), createdAt, id, name, type);
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
        return "DigitalDocument{" +
                "id=" + id +
                ", attendanceEvent=" + attendanceEvent +
                ", type=" + type +
                ", base64=" + Arrays.toString(base64) +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
