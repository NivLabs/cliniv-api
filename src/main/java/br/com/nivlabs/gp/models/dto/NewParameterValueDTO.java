package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;

@ApiModel("Requisição de novo parâmetro")
public class NewParameterValueDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1842321490525613622L;

    private String newValue;

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newValue == null) ? 0 : newValue.hashCode());
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
		NewParameterValueDTO other = (NewParameterValueDTO) obj;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewParameterValueDTO [newValue=" + newValue + "]";
	}
    
}
