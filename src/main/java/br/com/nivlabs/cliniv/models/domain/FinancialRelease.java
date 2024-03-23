package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.FinancialReleaseStatus;
import br.com.nivlabs.cliniv.enums.FinancialReleaseType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CAB_LANC_FIN")
public class FinancialRelease extends BaseObjectWithId<String> {

    @Id
    @Column(name = "UUID")
    private String id;

    @Column(name = "TITULO")
    private String title;
    @ManyToOne
    @JoinColumn(name = "ID_FORNECEDOR")
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA")
    private FinancialCategory category;
    @Column(name = "VALOR_LIQUIDO")
    private BigDecimal netValue;
    @Column(name = "VALOR_BRUTO")
    private BigDecimal grossValue;
    @Column(name = "TOTAL_DESCONTO")
    private BigDecimal discountValue;
    @Column(name = "DT_COMPETENCIA")
    private LocalDate competenceDate;
    @Column(name = "DT_VENCIMENTO")
    private LocalDate dueDate;
    @Column(name = "DH_PAGAMENTO")
    private LocalDateTime paymentDateTime;
    @Column(name = "TIPO_LANCAMENTO")
    @Enumerated(EnumType.STRING)
    private FinancialReleaseType type;
    @Column(name = "SITUACAO")
    @Enumerated(EnumType.STRING)
    private FinancialReleaseStatus status;
    @Column(name = "OBSERVACAO")
    private String observation;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public FinancialCategory getCategory() {
        return category;
    }

    public void setCategory(FinancialCategory category) {
        this.category = category;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDate getCompetenceDate() {
        return competenceDate;
    }

    public void setCompetenceDate(LocalDate competenceDate) {
        this.competenceDate = competenceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public FinancialReleaseType getType() {
        return type;
    }

    public void setType(FinancialReleaseType type) {
        this.type = type;
    }

    public FinancialReleaseStatus getStatus() {
        return status;
    }

    public void setStatus(FinancialReleaseStatus status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialRelease that = (FinancialRelease) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(supplier, that.supplier) && Objects.equals(category, that.category) && Objects.equals(netValue, that.netValue) && Objects.equals(grossValue, that.grossValue) && Objects.equals(discountValue, that.discountValue) && Objects.equals(competenceDate, that.competenceDate) && Objects.equals(dueDate, that.dueDate) && Objects.equals(paymentDateTime, that.paymentDateTime) && type == that.type && status == that.status && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, supplier, category, netValue, grossValue, discountValue, competenceDate, dueDate, paymentDateTime, type, status, observation);
    }

    @Override
    public String toString() {
        return "FinancialRelease{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", supplier=" + supplier +
                ", category=" + category +
                ", netValue=" + netValue +
                ", grossValue=" + grossValue +
                ", discountValue=" + discountValue +
                ", competenceDate=" + competenceDate +
                ", dueDate=" + dueDate +
                ", paymentDateTime=" + paymentDateTime +
                ", type=" + type +
                ", status=" + status +
                ", observation='" + observation + '\'' +
                '}';
    }
}
