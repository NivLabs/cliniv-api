package br.com.nivlabs.gp.models.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Operadora de plano de saúde")
public class HealthOperatorInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8630352370406381983L;

    @ApiModelProperty("Planos de saúde")
    private List<HealthPlanDTO> healthPlans;

    public HealthOperatorInfoDTO() {
        super();
    }

    public HealthOperatorInfoDTO(List<HealthPlanDTO> healthPlans) {
        super();
        this.healthPlans = healthPlans;
    }

    public List<HealthPlanDTO> getHealthPlans() {
        return healthPlans;
    }

    public void setHealthPlans(List<HealthPlanDTO> healthPlans) {
        this.healthPlans = healthPlans;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((healthPlans == null) ? 0 : healthPlans.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HealthOperatorInfoDTO other = (HealthOperatorInfoDTO) obj;
        if (healthPlans == null) {
            if (other.healthPlans != null)
                return false;
        } else if (!healthPlans.equals(other.healthPlans))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HealthOperatorInfoDTO [healthPlans=" + healthPlans + "]";
    }

}
