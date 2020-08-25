package br.com.nivlabs.gp.models.dto;

import br.com.nivlabs.gp.enums.PatientType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe PatientDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@ApiModel("Informações do Paciente")
public class PatientDTO extends PersonDTO {

    private static final long serialVersionUID = -1070682704153329772L;

    @ApiModelProperty("Plano de saúde")
    private HealthPlanDTO healthPlan;

    @ApiModelProperty("Número do SUS")
    private String susNumber;

    @ApiModelProperty("Tipo do paciente (Identificado ou não identificado)")
    private PatientType type;

	public HealthPlanDTO getHealthPlan() {
		return healthPlan;
	}

	public void setHealthPlan(HealthPlanDTO healthPlan) {
		this.healthPlan = healthPlan;
	}

	public String getSusNumber() {
		return susNumber;
	}

	public void setSusNumber(String susNumber) {
		this.susNumber = susNumber;
	}

	public PatientType getType() {
		return type;
	}

	public void setType(PatientType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((healthPlan == null) ? 0 : healthPlan.hashCode());
		result = prime * result + ((susNumber == null) ? 0 : susNumber.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientDTO other = (PatientDTO) obj;
		if (healthPlan == null) {
			if (other.healthPlan != null)
				return false;
		} else if (!healthPlan.equals(other.healthPlan))
			return false;
		if (susNumber == null) {
			if (other.susNumber != null)
				return false;
		} else if (!susNumber.equals(other.susNumber))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatientDTO [healthPlan=" + healthPlan + ", susNumber=" + susNumber + ", type=" + type + "]";
	}
    
    
}
