package br.com.nivlabs.gp.models.dto;

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

	private Long id;

	private String question;

	private MetaType metaType;

}
