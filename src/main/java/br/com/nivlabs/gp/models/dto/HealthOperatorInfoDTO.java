package br.com.nivlabs.gp.models.dto;

import java.util.List;

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
public class HealthOperatorInfoDTO extends DataTransferObjectBase {

	private static final long serialVersionUID = -8630352370406381983L;
	
	@ApiModelProperty("Planos de saúde")
    private List<HealthPlanDTO> healthPlans;

}
