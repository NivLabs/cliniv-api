package br.com.nivlabs.gp.models.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

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
@ApiModel("Requisição de Anamnese")
public class NewAnamnesisDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1489473679769549274L;

    @NotNull(message = "Informe o código do atendimento")
    @ApiModelProperty("Identificador do atendimento")
    private Long attendanceId;

    @NotNull(message = "Informe a sala ou leito do procedimento de anamnese")
    @ApiModelProperty("Sala ou leito onde a anamnese foi realizada")
    private Long roomOrBedId;

    private Set<AnamnesisDTO> listOfResponse = new HashSet<>();
}
