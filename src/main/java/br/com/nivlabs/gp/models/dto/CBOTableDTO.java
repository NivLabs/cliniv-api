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
@ApiModel("Tabela CBO")
public class CBOTableDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 480715163245338513L;

    @ApiModelProperty(name = "Identificador único")
    private Long id;
    @ApiModelProperty(name = "Descrição")
    private String description;
}
