package br.com.nivlabs.gp.models.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@NotEmpty
@ApiModel("Alergias do paciente")
public class PatientAllergiesDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 7849783306168301683L;

    @ApiModelProperty("Nomes das alergias")
    private List<String> descriptions;

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptions == null) ? 0 : descriptions.hashCode());
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
		PatientAllergiesDTO other = (PatientAllergiesDTO) obj;
		if (descriptions == null) {
			if (other.descriptions != null)
				return false;
		} else if (!descriptions.equals(other.descriptions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatientAllergiesDTO [descriptions=" + descriptions + "]";
	}
    
}
