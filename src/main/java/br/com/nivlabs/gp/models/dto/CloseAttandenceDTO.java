package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Objeto externo que representa o encerramento de um atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel("Encerramento de atendimento")
public class CloseAttandenceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4058913578939379862L;

    @ApiModelProperty("Tipo do evento de encerramento - ex: Alta médica por melhora")
    @NotNull(message = "Informe o tipo de evento de encerramento")
    private Long eventTypeId;

    @ApiModelProperty("Data/Hora do encerramento do atendimento")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @NotNull(message = "Informe a data e hora do evento e encerramento")
    private LocalDateTime datetime;

    @ApiModelProperty("Observações do encerramento (se houver)")
    private String observations;

    public CloseAttandenceDTO() {
        super();
    }

    public CloseAttandenceDTO(Long eventTypeId, LocalDateTime datetime, String observations) {
        super();
        this.eventTypeId = eventTypeId;
        this.datetime = datetime;
        this.observations = observations;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
        result = prime * result + ((eventTypeId == null) ? 0 : eventTypeId.hashCode());
        result = prime * result + ((observations == null) ? 0 : observations.hashCode());
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
        CloseAttandenceDTO other = (CloseAttandenceDTO) obj;
        if (datetime == null) {
            if (other.datetime != null)
                return false;
        } else if (!datetime.equals(other.datetime))
            return false;
        if (eventTypeId == null) {
            if (other.eventTypeId != null)
                return false;
        } else if (!eventTypeId.equals(other.eventTypeId))
            return false;
        if (observations == null) {
            if (other.observations != null)
                return false;
        } else if (!observations.equals(other.observations))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CloseAttandenceDTO [eventTypeId=" + eventTypeId + ", datetime=" + datetime + ", observations=" + observations + "]";
    }

}
