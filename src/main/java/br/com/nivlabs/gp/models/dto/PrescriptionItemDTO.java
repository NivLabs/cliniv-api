package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.PrescriptionItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Item da prescrição do paciente")
public class PrescriptionItemDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2268618756493323347L;

    @ApiModelProperty("Identificador único do item da prescrição do paciente")
    @NotNull(message = "Informe o identificador sequencial do item da prescrição")
    private Long id;

    @ApiModelProperty("Componente do item da prescrição")
    @NotEmpty(message = "Informe qual é o componente que o item representa (Descrição do Medicamento ou procedimento)")
    private String component;

    @ApiModelProperty("Quantidade de uso (Se houver)")
    private Double quantity;

    @ApiModelProperty("Unidade de medida (Se houver")
    private String measureUnit;

    @ApiModelProperty("Tipo do item (MEDICINE OU PROCEDURE")
    @NotNull(message = "Informe o tipo do item da prescrição")
    private PrescriptionItemType type;

    @ApiModelProperty("Instruções do item da prescrição (Se houver)")
    private String instructions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public PrescriptionItemType getType() {
		return type;
	}

	public void setType(PrescriptionItemType type) {
		this.type = type;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instructions == null) ? 0 : instructions.hashCode());
		result = prime * result + ((measureUnit == null) ? 0 : measureUnit.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PrescriptionItemDTO other = (PrescriptionItemDTO) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instructions == null) {
			if (other.instructions != null)
				return false;
		} else if (!instructions.equals(other.instructions))
			return false;
		if (measureUnit == null) {
			if (other.measureUnit != null)
				return false;
		} else if (!measureUnit.equals(other.measureUnit))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrescriptionItemDTO [id=" + id + ", component=" + component + ", quantity=" + quantity
				+ ", measureUnit=" + measureUnit + ", type=" + type + ", instructions=" + instructions + "]";
	}
    
}
