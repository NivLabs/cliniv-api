package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe EvolutionInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 5 de dez de 2019
 */
@ApiModel(description = "Evolução Clínica")
public class EvolutionInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6201888790941764512L;

    @ApiModelProperty("Identificador únimco da evolução clínica")
    private Long id;

    @ApiModelProperty("Identificador únimco do atendimento (Obrigatório)")
    @NotNull(message = "Informe o código do atendimento")
    private Long attendanceId;

    @ApiModelProperty("Identificador únimco da acomodação do paciente (Opcional se já houver registro com acomodação)")
    private Long accommodationId;

    @ApiModelProperty("Descrição da evolução (Obrigatório)")
    @NotNull(message = "Informe a descrição da evolução clínica")
    private String description;

    @ApiModelProperty("Data/Hora da leitura da evolução")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

    private String responsibleName;

    public EvolutionInfoDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accommodationId == null) ? 0 : accommodationId.hashCode());
        result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
        result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((responsibleName == null) ? 0 : responsibleName.hashCode());
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
        EvolutionInfoDTO other = (EvolutionInfoDTO) obj;
        if (accommodationId == null) {
            if (other.accommodationId != null)
                return false;
        } else if (!accommodationId.equals(other.accommodationId))
            return false;
        if (attendanceId == null) {
            if (other.attendanceId != null)
                return false;
        } else if (!attendanceId.equals(other.attendanceId))
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (responsibleName == null) {
            if (other.responsibleName != null)
                return false;
        } else if (!responsibleName.equals(other.responsibleName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EvolutionInfoDTO [id=" + id + ", attendanceId=" + attendanceId + ", accommodationId=" + accommodationId + ", description="
                + description + ", datetime=" + datetime + ", responsibleName=" + responsibleName + "]";
    }
}
