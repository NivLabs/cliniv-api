package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.Attendance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Anamnese")
public class AnamnesisDTO extends DataTransferObjectBase {
	private static final long serialVersionUID = -7700694137849034946L;

	@ApiModelProperty("Identificador da resposta da anamnese, não é obrigatório para a inserção")
	private Long id;

	@NotBlank(message = "Informar o atendimento é obrigatório.")
	@ApiModelProperty("Identificador do atendimento")
	private Long attendanceId;

	@NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.")
	@ApiModelProperty("Item da anamese com pertunga e tipo da resposta")
	private AnamnesisItemDTO anamnesisItem;

	@NotBlank(message = "Informar a resposta é obrigatório.")
	@ApiModelProperty("Resposta")
	private String response;

	@JsonIgnore
	public Anamnesis getAnamnesesDomainFromDTO() {
		Anamnesis domain = new Anamnesis();

		domain.setId(id);
		domain.setAttendance(new Attendance(attendanceId));
		domain.setQuestion(anamnesisItem.getQuestion());
		domain.setResponse(response);

		return domain;
	}

}
