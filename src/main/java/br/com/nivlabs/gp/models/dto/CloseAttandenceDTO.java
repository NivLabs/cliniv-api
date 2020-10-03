package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

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

    @ApiModelProperty("Identificador único do Tipo do evento de encerramento")
    @NotNull(message = "Informe o código interno único do tipo de evento de encerramento")
    private Long eventTypeId;

    @ApiModelProperty("Observações do encerramento (se houver)")
    private String observations;

    public CloseAttandenceDTO(Long eventTypeId, String observations) {
        this.eventTypeId = eventTypeId;
        this.observations = observations;
    }

    public CloseAttandenceDTO() {
        super();
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "CloseAttandenceDTO [eventTypeId=" + eventTypeId + ", observations=" + observations + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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

}
