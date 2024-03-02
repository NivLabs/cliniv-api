package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.Modality;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "OPERADORA")
public class HealthOperator extends BaseObjectWithId<Long> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthOperator that = (HealthOperator) o;
        return Objects.equals(id, that.id) && Objects.equals(ansCode, that.ansCode) && Objects.equals(cnpj, that.cnpj) && Objects.equals(companyName, that.companyName) && Objects.equals(fantasyName, that.fantasyName) && modality == that.modality && Objects.equals(healthPlans, that.healthPlans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ansCode, cnpj, companyName, fantasyName, modality, healthPlans);
    }

    @Override
    public String toString() {
        return "HealthOperator{" +
                "id=" + id +
                ", ansCode='" + ansCode + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", companyName='" + companyName + '\'' +
                ", fantasyName='" + fantasyName + '\'' +
                ", modality=" + modality +
                ", healthPlans=" + healthPlans +
                '}';
    }
}
