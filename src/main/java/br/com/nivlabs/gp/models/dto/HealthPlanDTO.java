package br.com.nivlabs.gp.models.dto;

import br.com.nivlabs.gp.enums.Abragency;
import br.com.nivlabs.gp.enums.ContractType;
import br.com.nivlabs.gp.enums.Segmentation;
import br.com.nivlabs.gp.models.domain.HealthOperator;
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
@ApiModel("Plano de saúde")
public class HealthPlanDTO extends DataTransferObjectBase {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6773781566276160000L;

	@ApiModelProperty("Identificador único  do plano")
    private Long id;

    @ApiModelProperty("Código do plano")
    private Long planCode;

    @ApiModelProperty("Nome comercial")
    private String commercialName;

    @ApiModelProperty("Segmentação")
    private Segmentation segmentation;

    @ApiModelProperty("Tipo do contrato")
    private ContractType contractType;

    @ApiModelProperty("Abrangência")
    private Abragency abragency;

    @ApiModelProperty("Tipo do plano")
    private String type;

}
