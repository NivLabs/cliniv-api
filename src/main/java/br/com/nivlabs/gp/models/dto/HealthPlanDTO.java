package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.Abragency;
import br.com.nivlabs.gp.enums.ContractType;
import br.com.nivlabs.gp.enums.Segmentation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Plano de saúde")
public class HealthPlanDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6773781566276160000L;

    @ApiModelProperty("Identificador único  do plano")
    private Long id;

    @ApiModelProperty("Código da operadora do plano")
    @NotNull(message = "O código da operadora do plano (ANS) é obrigatório")
    private String operatorCode;

    @ApiModelProperty("Nome da operadora do plano")
    private String operatorName;

    @ApiModelProperty("Código do plano")
    @NotNull(message = "O código do plano de saúde é obrigatório")
    private Long planCode;

    @ApiModelProperty("Nome comercial")
    @NotNull(message = "O nome comercial do plano de saúde é obrigatório")
    private String commercialName;

    @ApiModelProperty("Segmentação")
    @NotNull(message = "A segmentação do plano é obrigatória")
    private Segmentation segmentation;

    @ApiModelProperty("Tipo do contrato")
    @NotNull(message = "O tipo de contrato do plano é obrigatório")
    private ContractType contractType;

    @ApiModelProperty("Abrangência")
    @NotNull(message = "A abrangência do plano é obrigatória")
    private Abragency abrangency;

    @ApiModelProperty("Tipo do plano")
    private String type;

    @ApiModelProperty("Código do plano de saúde do paciente")
    private String patientPlanNumber;

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

    public Abragency getAbrangency() {
        return abrangency;
    }

    public void setAbrangency(Abragency abrangency) {
        this.abrangency = abrangency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatientPlanNumber() {
        return patientPlanNumber;
    }

    public void setPatientPlanNumber(String patientPlanNumber) {
        this.patientPlanNumber = patientPlanNumber;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        return "HealthPlanDTO [id=" + id + ", operatorCode=" + operatorCode + ", operatorName=" + operatorName + ", planCode=" + planCode
                + ", commercialName=" + commercialName + ", segmentation=" + segmentation + ", contractType=" + contractType
                + ", abrangency=" + abrangency + ", type=" + type + ", patientPlanNumber=" + patientPlanNumber + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((abrangency == null) ? 0 : abrangency.hashCode());
        result = prime * result + ((commercialName == null) ? 0 : commercialName.hashCode());
        result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((operatorCode == null) ? 0 : operatorCode.hashCode());
        result = prime * result + ((operatorName == null) ? 0 : operatorName.hashCode());
        result = prime * result + ((patientPlanNumber == null) ? 0 : patientPlanNumber.hashCode());
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
        if (abrangency != other.abrangency)
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
        if (operatorCode == null) {
            if (other.operatorCode != null)
                return false;
        } else if (!operatorCode.equals(other.operatorCode))
            return false;
        if (operatorName == null) {
            if (other.operatorName != null)
                return false;
        } else if (!operatorName.equals(other.operatorName))
            return false;
        if (patientPlanNumber == null) {
            if (other.patientPlanNumber != null)
                return false;
        } else if (!patientPlanNumber.equals(other.patientPlanNumber))
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

}
