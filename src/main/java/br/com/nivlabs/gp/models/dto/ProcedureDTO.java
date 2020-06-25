package br.com.nivlabs.gp.models.dto;

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
@ApiModel("Procedimento ou Evento")
public class ProcedureDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4991985626952633251L;

    @ApiModelProperty("Identificador do procedimento")
    private Long id;

    @ApiModelProperty("Descriçãod o procedimento")
    private String description;

    @ApiModelProperty("Indicativo de atividade do procedimento")
    private boolean active;
}
