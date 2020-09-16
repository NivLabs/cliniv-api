package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Classe NewPatientVisitDTO.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@ApiModel("Requisição de Atendimento")
public class NewAttandenceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2370290606342755763L;

    @ApiModelProperty("Código do paciente")
    @NotNull(message = "Informar o código do paciente é obrigatório")
    private Long patientId;

    @ApiModelProperty("Códgido do tipo do Evento")
    private Long eventTypeId;

    @ApiModelProperty("Código da especialidade procurada")
    @NotNull(message = "Informar o código da especialidade requisitada é obrigatório")
    private Long specialityId;

    @ApiModelProperty("Código do profissional responsável pela entrada")
    private Long responsibleId;

    @ApiModelProperty("Código da acomodação de origem do atendimento, ex: Triagem")
    @NotNull(message = "Informar a acomodação de origem é obrigatório")
    private Long accomodationId;

    @ApiModelProperty("Breve descrição do motivo da entrada|visita do paciente")
    @NotNull(message = "Informar o motivo da visita é obrigatório")
    private String entryCause;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Long getAccomodationId() {
        return accomodationId;
    }

    public void setAccomodationId(Long accomodationId) {
        this.accomodationId = accomodationId;
    }

    public String getEntryCause() {
        return entryCause;
    }

    public void setEntryCause(String entryCause) {
        this.entryCause = entryCause;
    }

    @Override
    public String toString() {
        return "NewAttandenceDTO [patientId=" + patientId + ", eventTypeId=" + eventTypeId + ", specialityId=" + specialityId
                + ", responsibleId=" + responsibleId + ", accomodationId=" + accomodationId + ", entryCause=" + entryCause + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accomodationId == null) ? 0 : accomodationId.hashCode());
        result = prime * result + ((entryCause == null) ? 0 : entryCause.hashCode());
        result = prime * result + ((eventTypeId == null) ? 0 : eventTypeId.hashCode());
        result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
        result = prime * result + ((responsibleId == null) ? 0 : responsibleId.hashCode());
        result = prime * result + ((specialityId == null) ? 0 : specialityId.hashCode());
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
        NewAttandenceDTO other = (NewAttandenceDTO) obj;
        if (accomodationId == null) {
            if (other.accomodationId != null)
                return false;
        } else if (!accomodationId.equals(other.accomodationId))
            return false;
        if (entryCause == null) {
            if (other.entryCause != null)
                return false;
        } else if (!entryCause.equals(other.entryCause))
            return false;
        if (eventTypeId == null) {
            if (other.eventTypeId != null)
                return false;
        } else if (!eventTypeId.equals(other.eventTypeId))
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
        if (specialityId == null) {
            if (other.specialityId != null)
                return false;
        } else if (!specialityId.equals(other.specialityId))
            return false;
        return true;
    }

}
