package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe VisitEventDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de nov de 2019
 */

@ApiModel("Evento do Atendimento")
public class AttendanceEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5192545539633937184L;

    @ApiModelProperty("Identificador único do evento do atendimento")
    private Long id;

    @ApiModelProperty("Data e hora do evento do atendimento")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

    @ApiModelProperty("Descrição do evento do atendimento")
    private String description;

    @ApiModelProperty("Documentos gerados pelo evento do atendimento")
    private List<DigitalDocumentDTO> documents;

    @ApiModelProperty("Acomodação onde o evento foi ou será realizado")
    private AccommodationDTO accommodation;

    public AttendanceEventDTO() {
        super();
    }

    public AttendanceEventDTO(Long id, LocalDateTime datetime, String description, List<DigitalDocumentDTO> documents,
            AccommodationDTO accommodation) {
        super();
        this.id = id;
        this.datetime = datetime;
        this.description = description;
        this.documents = documents;
        this.accommodation = accommodation;
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

    @Override
    public String toString() {
        return "AttendanceEventDTO [id=" + id + ", datetime=" + datetime + ", description=" + description + ", documents=" + documents
                + ", accommodation=" + accommodation + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accommodation == null) ? 0 : accommodation.hashCode());
        result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((documents == null) ? 0 : documents.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        AttendanceEventDTO other = (AttendanceEventDTO) obj;
        if (accommodation == null) {
            if (other.accommodation != null)
                return false;
        } else if (!accommodation.equals(other.accommodation))
            return false;
        if (datetime == null) {
            if (other.datetime != null)
                return false;
        } else if (!datetime.equals(other.datetime))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (documents == null) {
            if (other.documents != null)
                return false;
        } else if (!documents.equals(other.documents))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
