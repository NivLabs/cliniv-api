package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.EventType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Evento de Atendimento")
public class NewAttendanceEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 9069758427814475745L;

    @Schema(description = "Identificador do Atendimento")
    @NotNull(message = "O código do atendimeno é obrigatório")
    private Long attendanceId;

    @Schema(description = "Data/Hora do evento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime eventDateTime;

    @Schema(description = "Dovumentos do evento (se houver)")
    private List<DigitalDocumentDTO> documents = new ArrayList<>();

    @Schema(description = "Tipo do evento")
    @NotNull(message = "Informar o tipo do evento é obrigatório")
    private EventType eventType;

    @Schema(description = "Obsevações (se houver)")
    private String observations;

    @Schema(description = "Procedimento do evento (se houver)")
    private ProcedureInfoDTO procedure;

    @Schema(description = "Responsável por gerar o evento")
    @NotNull(message = "Obrigado informar um responsável")
    private ResponsibleInfoDTO responsible;

    @NotNull(message = "Informar a sala ou leito em que o evento foi realizado é obrigatório")
    @Schema(description = "Sala ou leito em que o evento ocorreu")
    private AccommodationDTO accommodation;

    /**
     * @return the attendanceId
     */
    public Long getAttendanceId() {
        return attendanceId;
    }

    /**
     * @param attendanceId the attendanceId to set
     */
    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * @return the eventDateTime
     */
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    /**
     * @param eventDateTime the eventDateTime to set
     */
    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    /**
     * @return the documents
     */
    public List<DigitalDocumentDTO> getDocuments() {
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(List<DigitalDocumentDTO> documents) {
        this.documents = documents;
    }

    /**
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the observations
     */
    public String getObservations() {
        return observations;
    }

    /**
     * @param observations the observations to set
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     * @return the procedure
     */
    public ProcedureInfoDTO getProcedure() {
        return procedure;
    }

    /**
     * @param procedure the procedure to set
     */
    public void setProcedure(ProcedureInfoDTO procedure) {
        this.procedure = procedure;
    }

    /**
     * @return the responsible
     */
    public ResponsibleInfoDTO getResponsible() {
        return responsible;
    }

    /**
     * @param responsible the responsible to set
     */
    public void setResponsible(ResponsibleInfoDTO responsible) {
        this.responsible = responsible;
    }

    /**
     * @return the accommodation
     */
    public AccommodationDTO getAccommodation() {
        return accommodation;
    }

    /**
     * @param accommodation the accommodation to set
     */
    public void setAccommodation(AccommodationDTO accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NewAttendanceEventDTO [attendanceId=");
        builder.append(attendanceId);
        builder.append(", eventDateTime=");
        builder.append(eventDateTime);
        builder.append(", documents=");
        builder.append(documents);
        builder.append(", eventType=");
        builder.append(eventType);
        builder.append(", observations=");
        builder.append(observations);
        builder.append(", procedure=");
        builder.append(procedure);
        builder.append(", responsible=");
        builder.append(responsible);
        builder.append(", accommodation=");
        builder.append(accommodation);
        builder.append("]");
        return builder.toString();
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

}
