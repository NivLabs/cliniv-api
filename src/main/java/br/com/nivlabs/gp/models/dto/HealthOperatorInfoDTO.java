package br.com.nivlabs.gp.models.dto;

import java.util.List;

import br.com.nivlabs.gp.enums.Modality;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Operadora de plano de saúde")
public class HealthOperatorInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8630352370406381983L;

    @ApiModelProperty("Identificador único  da operadora")
    private Long id;

    @ApiModelProperty("Código ANS")
    private String ansCode;

    @ApiModelProperty("CNPJ")
    private String cnpj;

    @ApiModelProperty("Razão social")
    private String companyName;

    @ApiModelProperty("Nome fantasia")
    private String fantasyName;

    @ApiModelProperty("Modalidade da operadora")
    private Modality modality;

    @ApiModelProperty("Planos de saúde")
    private List<HealthPlanDTO> healthPlans;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnsCode() {
        return ansCode;
    }

    public void setAnsCode(String ansCode) {
        this.ansCode = ansCode;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
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
        result = prime * result + ((ansCode == null) ? 0 : ansCode.hashCode());
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((fantasyName == null) ? 0 : fantasyName.hashCode());
        result = prime * result + ((healthPlans == null) ? 0 : healthPlans.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modality == null) ? 0 : modality.hashCode());
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
        if (ansCode == null) {
            if (other.ansCode != null)
                return false;
        } else if (!ansCode.equals(other.ansCode))
            return false;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
            return false;
        if (fantasyName == null) {
            if (other.fantasyName != null)
                return false;
        } else if (!fantasyName.equals(other.fantasyName))
            return false;
        if (healthPlans == null) {
            if (other.healthPlans != null)
                return false;
        } else if (!healthPlans.equals(other.healthPlans))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (modality != other.modality)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HealthOperatorInfoDTO [id=" + id + ", ansCode=" + ansCode + ", cnpj=" + cnpj + ", companyName=" + companyName
                + ", fantasyName=" + fantasyName + ", modality=" + modality + ", healthPlans=" + healthPlans + "]";
    }

}
