package br.com.nivlabs.cliniv.models.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import br.com.nivlabs.cliniv.enums.Modality;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;

@Entity
@Table(name = "OPERADORA")
public class HealthOperator extends BaseObjectWithId {

    private static final long serialVersionUID = -4784137469843956304L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COD_ANS")
    private String ansCode;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "RAZAO_SOCIAL")
    private String companyName;

    @Column(name = "NOME_FANTASIA")
    private String fantasyName;

    @Column(name = "MODALIDADE")
    @Enumerated(EnumType.STRING)
    private Modality modality;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "healthOperator")
    private List<HealthPlan> healthPlans = new ArrayList<>();

    public HealthOperator() {
        super();
    }

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

    public List<HealthPlan> getHealthPlans() {
        return healthPlans;
    }

    public void setHealthPlans(List<HealthPlan> healthPlans) {
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
        HealthOperator other = (HealthOperator) obj;
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
        return "HealthOperator [id=" + id + ", ansCode=" + ansCode + ", cnpj=" + cnpj + ", companyName=" + companyName + ", fantasyName="
                + fantasyName + ", modality=" + modality + ", healthPlans=" + healthPlans + "]";
    }

}
