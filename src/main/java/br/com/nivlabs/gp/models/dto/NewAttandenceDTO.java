package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Classe NewPatientVisitDTO.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Requisição de Atendimento")
public class NewAttandenceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2370290606342755763L;

    @ApiModelProperty("Código do paciente")
    @NotNull(message = "Informar o código do paciente é obrigatório")
    private Long patientId;

    @ApiModelProperty("Códgido do tipo do Evento")
    private Long eventTypeId;

    @ApiModelProperty("Código do profissional responsável pela entrada")
    private Long responsibleId;

    @ApiModelProperty("Código do setor de origem do atendimento, ex: Recepção")
    @NotNull(message = "Informar o setor de origem é obrigatório")
    private Long sectorId;

    @ApiModelProperty("Breve descrição do motivo da entrada|visita do paciente")
    @NotNull(message = "Informar o motivo da visita é obrigatório")
    private String entryCause;

}
