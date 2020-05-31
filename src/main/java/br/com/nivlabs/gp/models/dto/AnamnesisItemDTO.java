package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.models.enums.MetaType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Anamnesis Item")
public class AnamnesisItemDTO extends DataTransferObjectBase {
	private static final long serialVersionUID = -1666755500493520346L;

	@NotNull
	private Long id;

	@NotNull(message = "Informe a questão")
	private String question;

	@NotNull(message = "Informe o tipo da questão")
	private MetaType metaType;

}
