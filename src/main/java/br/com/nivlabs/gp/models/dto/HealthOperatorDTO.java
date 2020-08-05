package br.com.nivlabs.gp.models.dto;

import br.com.nivlabs.gp.enums.Modality;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Operadora de plano de saúde")
public class HealthOperatorDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8486624597576546424L;

    @ApiModelProperty("Identificador único  da operadora")
    private Long id;

    @ApiModelProperty("Código ANS")
    private Long ansCode;

    @ApiModelProperty("CNPJ")
    private String cnpj;

    @ApiModelProperty("Razão social")
    private String companyName;

    @ApiModelProperty("Nome fantasia")
    private String fantasyName;

    @ApiModelProperty("Modalidade da operadora")
    private Modality modality;

}
