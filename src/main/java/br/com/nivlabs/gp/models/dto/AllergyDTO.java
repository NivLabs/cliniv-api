package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Allergy")
public class AllergyDTO extends DataTransferObjectBase {

	private static final long serialVersionUID = 8445432329934001912L;

	private Long id;

	@NotNull(message = "Você deve informa o nome do componente que você tem alergia")
	private String allergicComponent;

	private String observation;
}
