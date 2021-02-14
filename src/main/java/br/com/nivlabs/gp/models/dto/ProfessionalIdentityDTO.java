package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;

/**
 * Registro profissional do responsável de saúde (CRM, CRO, CRP, COREN, etc...)
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "Identidade Profissional")
public class ProfessionalIdentityDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5712585514927819479L;

    private String registerType;

    private String registerValue;

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getRegisterValue() {
		return registerValue;
	}

	public void setRegisterValue(String registerValue) {
		this.registerValue = registerValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registerType == null) ? 0 : registerType.hashCode());
		result = prime * result + ((registerValue == null) ? 0 : registerValue.hashCode());
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
		ProfessionalIdentityDTO other = (ProfessionalIdentityDTO) obj;
		if (registerType == null) {
			if (other.registerType != null)
				return false;
		} else if (!registerType.equals(other.registerType))
			return false;
		if (registerValue == null) {
			if (other.registerValue != null)
				return false;
		} else if (!registerValue.equals(other.registerValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProfessionalIdentityDTO [registerType=" + registerType + ", registerValue=" + registerValue + "]";
	}
    
}
