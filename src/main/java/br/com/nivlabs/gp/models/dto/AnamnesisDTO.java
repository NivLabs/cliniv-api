package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.Attendance;
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

	@NotBlank(message = "Informar o atendimento é obrigatório.")
	private Long attendanceId;

	@NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.")
	private AnamnesisItemDTO anamnesisItem;

	@NotBlank(message = "Informar a resposta é obrigatório.")
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
