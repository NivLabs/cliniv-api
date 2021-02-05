package br.com.nivlabs.gp.models.dto;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * Classe ReportLayoutDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 04 de fevereiro de 2021
 */
@ApiModel("Parametros de Relatório")
public class ReportParameterDTO extends DataTransferObjectBase {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4069593034981336944L;
	
	@ApiModelProperty("Lista de parâmetros do layout")
	private Map<String, String> params;

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((params == null) ? 0 : params.hashCode());
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
		ReportParameterDTO other = (ReportParameterDTO) obj;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}
	
	
}
