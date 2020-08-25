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

    @ApiModelProperty("Código do profissional responsável pela entrada")
    private Long responsibleId;

    @ApiModelProperty("Código do setor de origem do atendimento, ex: Recepção")
    @NotNull(message = "Informar o setor de origem é obrigatório")
    private Long sectorId;

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

	public Long getResponsibleId() {
		return responsibleId;
	}

	public void setResponsibleId(Long responsibleId) {
		this.responsibleId = responsibleId;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getEntryCause() {
		return entryCause;
	}

	public void setEntryCause(String entryCause) {
		this.entryCause = entryCause;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entryCause == null) ? 0 : entryCause.hashCode());
		result = prime * result + ((eventTypeId == null) ? 0 : eventTypeId.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + ((responsibleId == null) ? 0 : responsibleId.hashCode());
		result = prime * result + ((sectorId == null) ? 0 : sectorId.hashCode());
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
		if (sectorId == null) {
			if (other.sectorId != null)
				return false;
		} else if (!sectorId.equals(other.sectorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewAttandenceDTO [patientId=" + patientId + ", eventTypeId=" + eventTypeId + ", responsibleId="
				+ responsibleId + ", sectorId=" + sectorId + ", entryCause=" + entryCause + "]";
	}

}
