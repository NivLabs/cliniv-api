package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Documento digital de eventos de atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel("Documento Digital")
public class DigitalDocumentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -870502165996369141L;

    @ApiModelProperty("Identificador único do documento")
    private Long id;

    @ApiModelProperty("Identificador do evento de atendimento")
    private Long attendanceEventId;

    @ApiModelProperty("Tipo do documento")
    private DigitalDocumentType type;

    @ApiModelProperty("Base64 do documento")
    private String base64;

    @ApiModelProperty("Nome ou título do documento")
    private String name;

    @ApiModelProperty("Data da criação do documento")
    private LocalDateTime createdAt;

    public DigitalDocumentDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttendanceEventId() {
        return attendanceEventId;
    }

    public void setAttendanceEventId(Long attendanceEventId) {
        this.attendanceEventId = attendanceEventId;
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attendanceEventId == null) ? 0 : attendanceEventId.hashCode());
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
        DigitalDocumentDTO other = (DigitalDocumentDTO) obj;
        if (attendanceEventId == null) {
            if (other.attendanceEventId != null)
                return false;
        } else if (!attendanceEventId.equals(other.attendanceEventId))
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

    @Override
    public String toString() {
        return "DigitalDocumentDTO [id=" + id + ", attendanceEventId=" + attendanceEventId + ", type=" + type + ", base64=" + base64
                + ", name=" + name + ", createdAt=" + createdAt + "]";
    }

}
