package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import br.com.nivlabs.gp.models.enums.EntryType;
import br.com.nivlabs.gp.models.enums.PatientType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de nov de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Attendance")
public class AttendanceDTO extends DataTransferObjectBase {

	private static final long serialVersionUID = -7717106082371494163L;

	@ApiModelProperty("Identificador único do atendimento")
	private Long id;

	@ApiModelProperty("Primeiro nome")
	private String firstName;

	@ApiModelProperty("Último nome")
	private String lastName;

	@ApiModelProperty("Data da entrada")
	private LocalDateTime entryDatetime;

	@ApiModelProperty("Causa da Entrada")
	private String entryCause;

	@ApiModelProperty("Retorna true se já estiver sido finalizada")
	private Boolean isFinished;

	@ApiModelProperty("Tipo da entrada")
	private EntryType type;

	@ApiModelProperty("Tipo do paciente")
	private PatientType patientType;

	@ApiModelProperty("Identificador do paciente")
	private Long patientId;

	@ApiModelProperty("Código SUS")
	private String susNumber;

	@ApiModelProperty("Descrição|nome do setor")
	private String sectorDescripton;
}
