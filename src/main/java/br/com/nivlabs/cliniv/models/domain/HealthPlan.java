package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.Abragency;
import br.com.nivlabs.cliniv.enums.ContractType;
import br.com.nivlabs.cliniv.enums.Segmentation;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "PLANO")
public class HealthPlan extends BaseObjectWithId<Long> {

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
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthPlan that = (HealthPlan) o;
        return Objects.equals(id, that.id) && Objects.equals(planCode, that.planCode) && Objects.equals(commercialName, that.commercialName) && segmentation == that.segmentation && contractType == that.contractType && abrangency == that.abrangency && Objects.equals(type, that.type) && Objects.equals(healthOperator, that.healthOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, planCode, commercialName, segmentation, contractType, abrangency, type, healthOperator);
    }

    @Override
    public String toString() {
        return "HealthPlan{" +
                "id=" + id +
                ", planCode=" + planCode +
                ", commercialName='" + commercialName + '\'' +
                ", segmentation=" + segmentation +
                ", contractType=" + contractType +
                ", abrangency=" + abrangency +
                ", type='" + type + '\'' +
                ", healthOperator=" + healthOperator +
                '}';
    }

}
