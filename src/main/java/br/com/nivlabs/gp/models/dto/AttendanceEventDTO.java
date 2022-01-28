package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe VisitEventDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de nov de 2019
 */

@Schema(description = "Evento do Atendimento")
public class AttendanceEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5192545539633937184L;

    @Schema(description = "Identificador único do evento do atendimento")
    private Long id;

    @Schema(description = "Data e hora do evento do atendimento")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

    @Schema(description = "Descrição do evento do atendimento")
    private String description;

    @Schema(description = "Documentos gerados pelo evento do atendimento")
    private List<DigitalDocumentDTO> documents;

    @Schema(description = "Acomodação onde o evento foi ou será realizado")
    private AccommodationDTO accommodation;

    @Schema(description = "Profissional que adicionou o evento")
    private ResponsibleDTO professional;

    public AttendanceEventDTO() {
        super();
    }

    public AttendanceEventDTO(Long id, LocalDateTime datetime, String description, List<DigitalDocumentDTO> documents,
            AccommodationDTO accommodation, ResponsibleDTO professional) {
        super();
        this.id = id;
        this.datetime = datetime;
        this.description = description;
        this.documents = documents;
        this.accommodation = accommodation;
        this.professional = professional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DigitalDocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DigitalDocumentDTO> documents) {
        this.documents = documents;
    }

    public AccommodationDTO getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationDTO accommodation) {
        this.accommodation = accommodation;
    }

    public ResponsibleDTO getProfessional() {
        return professional;
    }

    public void setProfessional(ResponsibleDTO professional) {
        this.professional = professional;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AttendanceEventDTO [id=");
        builder.append(id);
        builder.append(", datetime=");
        builder.append(datetime);
        builder.append(", description=");
        builder.append(description);
        builder.append(", documents=");
        builder.append(documents);
        builder.append(", accommodation=");
        builder.append(accommodation);
        builder.append(", professional=");
        builder.append(professional);
        builder.append("]");
        return builder.toString();
    }

}
