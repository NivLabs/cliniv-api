package br.com.nivlabs.gp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.Abragency;
import br.com.nivlabs.gp.enums.ContractType;
import br.com.nivlabs.gp.enums.Segmentation;
import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "PLANO")
public class HealthPlan extends BaseObjectWithId {

    private static final long serialVersionUID = -7856635198888818925L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COD_PLAN")
    private Long planCode;

    @Column(name = "NOME_COMERCIAL")
    private String commercialName;

    @Column(name = "SEGMENTACAO")
    @Enumerated(EnumType.STRING)
    private Segmentation segmentation;

    @Column(name = "TIPO_CONTRATO")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(name = "ABRANGENCIA")
    @Enumerated(EnumType.STRING)
    private Abragency abrangency;

    @Column(name = "TIPO_PLANO")
    private String type;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OPERADORA")
    private HealthOperator healthOperator;

    public HealthPlan() {
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

    public HealthOperator getHealthOperator() {
        return healthOperator;
    }

    public void setHealthOperator(HealthOperator healthOperator) {
        this.healthOperator = healthOperator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((abrangency == null) ? 0 : abrangency.hashCode());
        result = prime * result + ((commercialName == null) ? 0 : commercialName.hashCode());
        result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
        result = prime * result + ((healthOperator == null) ? 0 : healthOperator.hashCode());
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
        HealthPlan other = (HealthPlan) obj;
        if (abrangency != other.abrangency)
            return false;
        if (commercialName == null) {
            if (other.commercialName != null)
                return false;
        } else if (!commercialName.equals(other.commercialName))
            return false;
        if (contractType != other.contractType)
            return false;
        if (healthOperator == null) {
            if (other.healthOperator != null)
                return false;
        } else if (!healthOperator.equals(other.healthOperator))
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
        return "HealthPlan [id=" + id + ", planCode=" + planCode + ", commercialName=" + commercialName + ", segmentation=" + segmentation
                + ", contractType=" + contractType + ", abrangency=" + abrangency + ", type=" + type + ", healthOperator=" + healthOperator
                + "]";
    }

}
