package br.com.nivlabs.gp.models.dto;

import java.util.HashSet;
import java.util.Set;

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
@ApiModel("New Anamnesis Request")
public class NewAnamnesisDTO extends DataTransferObjectBase {

	private static final long serialVersionUID = 1489473679769549274L;

	@NotNull(message = "Informe o código do atendimento")
	private Long attendanceId;

	private Set<AnamnesisDTO> listOfResponse = new HashSet<>();
}
