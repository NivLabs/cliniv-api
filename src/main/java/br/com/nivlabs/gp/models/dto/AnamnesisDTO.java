package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.AnamnesisItem;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Anamnesis")
public class AnamnesisDTO extends DataTransferObjectBase {
	private static final long serialVersionUID = -7700694137849034946L;

	private Long id;

	@NotBlank(message = "Informar o ATENDIMENTO é obrigatório.")
	private Long attendanceId;

	@NotBlank(message = "Informar o ITEM ANAMNESIS é obrigatório.")
	private AnamnesisItemDTO anamnesisItem;

	@NotBlank(message = "Informar a RESPOSTA é obrigatório.")
	private String response;

	@JsonIgnore
	public Anamnesis getAnamnesesDomainFromDTO() {
		Anamnesis domain = new Anamnesis();

		domain.setId(id);
		domain.setAnamnesisItem(
				new AnamnesisItem(anamnesisItem.getId(), anamnesisItem.getQuestion(), anamnesisItem.getMetaType()));
		domain.setResponse(response);

		return domain;
	}

}
