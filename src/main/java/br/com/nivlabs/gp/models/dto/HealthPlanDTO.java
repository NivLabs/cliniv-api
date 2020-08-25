package br.com.nivlabs.gp.models.dto;

import br.com.nivlabs.gp.enums.Abragency;
import br.com.nivlabs.gp.enums.ContractType;
import br.com.nivlabs.gp.enums.Segmentation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    public HealthPlanDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanCode() {
        return planCode;
    }

    public void setPlanCode(Long planCode) {
        this.planCode = planCode;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public Segmentation getSegmentation() {
        return segmentation;
    }

    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Abragency getAbragency() {
        return abragency;
    }

    public void setAbragency(Abragency abragency) {
        this.abragency = abragency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((abragency == null) ? 0 : abragency.hashCode());
        result = prime * result + ((commercialName == null) ? 0 : commercialName.hashCode());
        result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((planCode == null) ? 0 : planCode.hashCode());
        result = prime * result + ((segmentation == null) ? 0 : segmentation.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        HealthPlanDTO other = (HealthPlanDTO) obj;
        if (abragency != other.abragency)
            return false;
        if (commercialName == null) {
            if (other.commercialName != null)
                return false;
        } else if (!commercialName.equals(other.commercialName))
            return false;
        if (contractType != other.contractType)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (planCode == null) {
            if (other.planCode != null)
                return false;
        } else if (!planCode.equals(other.planCode))
            return false;
        if (segmentation != other.segmentation)
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HealthPlanDTO [id=" + id + ", planCode=" + planCode + ", commercialName=" + commercialName + ", segmentation="
                + segmentation + ", contractType=" + contractType + ", abragency=" + abragency + ", type=" + type + "]";
    }

}
