package br.com.nivlabs.gp.models.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.enums.Modality;
import br.com.nivlabs.gp.exception.HttpException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Operadora de plano de saúde")
public class HealthOperatorInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8630352370406381983L;

    @ApiModelProperty("Identificador único  da operadora")
    private Long id;

    @ApiModelProperty("Código ANS")
    @NotNull(message = "Informe o código ANS da operadora")
    private String ansCode;

    @ApiModelProperty("Documento CNPJ")
    @NotNull(message = "Informe o CNPJ da operadora")
    private DocumentDTO document;

    @ApiModelProperty("Razão social")
    @NotNull(message = "Informe a razão social da operadora")
    private String companyName;

    @ApiModelProperty("Nome fantasia")
    @NotNull(message = "Informe o nome fantasia da operadora")
    private String fantasyName;

    @ApiModelProperty("Modalidade da operadora")
    @NotNull(message = "Informe a modalidade da operadora")
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

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        if (document.getType().equals(DocumentType.CNPJ))
            this.document = document;
        else
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O tipo do documento deve ser CNPJ");
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
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((document == null) ? 0 : document.hashCode());
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
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
            return false;
        if (document == null) {
            if (other.document != null)
                return false;
        } else if (!document.equals(other.document))
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

}
