package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel("Prescrição do paciente")
public class PrescriptionInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6743345972601222205L;

    @ApiModelProperty("Identificador único da prescrição")
    private Long id;

    @ApiModelProperty("Identificador único do atendimento")
    private Long attendanceId;

    @ApiModelProperty("Identificador único do responsável da prescrição (Quem criou)")
    private Long responsibleId;

    @ApiModelProperty("Lista de itens da prescrição")
    private List<PrescriptionItemDTO> item = new ArrayList<>();

    @ApiModelProperty("Data e hora de criação da prescrição")
    private LocalDateTime createdAt;

    @ApiModelProperty("Flag de status da prescrição (true - ativa ")
    private boolean status;

}