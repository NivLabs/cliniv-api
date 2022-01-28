package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.nivlabs.gp.enums.EventType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Objeto que representa um evento do atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@Schema(description = "Informações do evento do atendimento")
public class AttendanceEventInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4230965183543396561L;

    @Schema(description = "Identificador único do evento do atendimento")
    private Long id;
    @Schema(description = "Identificador único do paciente do evento do atendimento")
    private Long patientId;
    @Schema(description = "Identificador único do profissional responsável pelo evento do atendimento")
    private Long responsibleId;
    @Schema(description = "Identificador único do atendimento")
    private Long attendanceId;
    @Schema(description = "Documentos gerados pelo evento do atendimento")
    private List<DigitalDocumentDTO> documents;
    @Schema(description = "Título do evento do atendimento")
    private String title;
    @Schema(description = "Documentos gerados pelo evento do atendimento")
    private String observations;
    private LocalDateTime eventDateTime;
    private EventType type;

    public AttendanceEventInfoDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public List<DigitalDocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DigitalDocumentDTO> documents) {
        this.documents = documents;
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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AttendanceEventInfoDTO [id=" + id + ", patientId=" + patientId + ", responsibleId=" + responsibleId + ", attendanceId="
                + attendanceId + ", documents=" + documents + ", title=" + title + ", observations=" + observations + ", eventDateTime="
                + eventDateTime + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
        result = prime * result + ((documents == null) ? 0 : documents.hashCode());
        result = prime * result + ((eventDateTime == null) ? 0 : eventDateTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((observations == null) ? 0 : observations.hashCode());
        result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
        result = prime * result + ((responsibleId == null) ? 0 : responsibleId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        AttendanceEventInfoDTO other = (AttendanceEventInfoDTO) obj;
        if (attendanceId == null) {
            if (other.attendanceId != null)
                return false;
        } else if (!attendanceId.equals(other.attendanceId))
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
        if (patientId == null) {
            if (other.patientId != null)
                return false;
        } else if (!patientId.equals(other.patientId))
            return false;
        if (responsibleId == null) {
            if (other.responsibleId != null)
                return false;
        } else if (!responsibleId.equals(other.responsibleId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
