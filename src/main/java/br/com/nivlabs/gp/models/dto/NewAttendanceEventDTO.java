package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Evento de Atendimento")
public class NewAttendanceEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 9069758427814475745L;

    @ApiModelProperty("Identificador do Atendimento")
    @NotNull(message = "O código do atendimeno é obrigatório")
    private Long attendanceId;

    @ApiModelProperty("Data/Hora do evento")
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull(message = "A data do evento não pode ser nula")
    private LocalDateTime eventDateTime;

    @ApiModelProperty("Dovumentos do evento (se houver)")
    private List<DigitalDocumentDTO> documents = new ArrayList<>();

    @ApiModelProperty("Tipo do evento")
    @NotNull(message = "Informar o tipo do evento é obrigatório")
    private EventTypeDTO eventType;

    @ApiModelProperty("Obsevações (se houver)")
    private String observations;

    @ApiModelProperty("Procedimento do evento (se houver)")
    private ProcedureDTO procedure;

    @ApiModelProperty("Responsável por gerar o evento")
    @NotNull(message = "Obrigado informar um responsável")
    private ResponsibleInfoDTO responsible;

    @NotNull(message = "Informar a sala ou leito em que o evento foi realizado é obrigatório")
    @ApiModelProperty("Sala ou leito em que o evento ocorreu")
    private AccommodationDTO accommodation;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public List<DigitalDocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DigitalDocumentDTO> documents) {
        this.documents = documents;
    }

    public EventTypeDTO getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeDTO eventType) {
        this.eventType = eventType;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public ProcedureDTO getProcedure() {
        return procedure;
    }

    public void setProcedure(ProcedureDTO procedure) {
        this.procedure = procedure;
    }

    public ResponsibleInfoDTO getResponsible() {
        return responsible;
    }

    public void setResponsible(ResponsibleInfoDTO responsible) {
        this.responsible = responsible;
    }

    public AccommodationDTO getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationDTO accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accommodation == null) ? 0 : accommodation.hashCode());
        result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
        result = prime * result + ((documents == null) ? 0 : documents.hashCode());
        result = prime * result + ((eventDateTime == null) ? 0 : eventDateTime.hashCode());
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + ((observations == null) ? 0 : observations.hashCode());
        result = prime * result + ((procedure == null) ? 0 : procedure.hashCode());
        result = prime * result + ((responsible == null) ? 0 : responsible.hashCode());
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
        NewAttendanceEventDTO other = (NewAttendanceEventDTO) obj;
        if (accommodation == null) {
            if (other.accommodation != null)
                return false;
        } else if (!accommodation.equals(other.accommodation))
            return false;
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
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
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
        return true;
    }

    @Override
    public String toString() {
        return "NewAttendanceEventDTO [attendanceId=" + attendanceId + ", eventDateTime=" + eventDateTime
                + ", documents=" + documents + ", eventType=" + eventType + ", observations=" + observations
                + ", procedure=" + procedure + ", responsible=" + responsible + ", accommodation=" + accommodation + "]";
    }

}
