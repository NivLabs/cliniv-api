package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.EventType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Objeto externo que representa o encerramento de um atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "Encerramento de atendimento")
public class CloseAttandenceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4058913578939379862L;

    @ApiModelProperty("Identificador do Tipo do evento de encerramento")
    @NotNull(message = "Informe o tipo de evento de encerramento")
    private EventType eventType;

    @ApiModelProperty("Observações do encerramento (se houver)")
    private String observations;

    public CloseAttandenceDTO(EventType eventType, String observations) {
        this.eventType = eventType;
        this.observations = observations;
    }

    public CloseAttandenceDTO() {
        super();
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "CloseAttandenceDTO [eventType=" + eventType + ", observations=" + observations + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
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
        if (eventType != other.eventType)
            return false;
        if (observations == null) {
            if (other.observations != null)
                return false;
        } else if (!observations.equals(other.observations))
            return false;
        return true;
    }

}
