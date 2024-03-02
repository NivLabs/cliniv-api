package br.com.nivlabs.cliniv.models.domain.tiss;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.dto.ProcedureInfoDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "PROCEDIMENTO")
public class Procedure extends BaseObjectWithId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "VALOR_BASE")
    private BigDecimal baseValue;

    @Column(name = "PERIOCIDADE")
    private String frequency;

    @Column(name = "REQ_AUTORIZACAO_ESPECIAL")
    private boolean specialAuthorization;

    @Column(name = "REQ_AUDIT_PREV")
    private boolean previousAudit;

    @Column(name = "REQ_ESPECIALIDADE")
    private boolean specialty;

    @Column(name = "IDADE_MAX")
    private String maxAge;

    @Column(name = "IDADE_MIN")
    private String minAge;

    public Procedure() {
        super();
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(BigDecimal baseValue) {
        this.baseValue = baseValue;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isSpecialAuthorization() {
        return specialAuthorization;
    }

    public void setSpecialAuthorization(boolean specialAuthorization) {
        this.specialAuthorization = specialAuthorization;
    }

    public boolean isPreviousAudit() {
        return previousAudit;
    }

    public void setPreviousAudit(boolean previousAudit) {
        this.previousAudit = previousAudit;
    }

    public boolean isSpecialty() {
        return specialty;
    }

    public void setSpecialty(boolean specialty) {
        this.specialty = specialty;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProcedureInfoDTO getDTO() {
        ProcedureInfoDTO dto = new ProcedureInfoDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Procedure [id=");
        builder.append(id);
        builder.append(", description=");
        builder.append(description);
        builder.append(", baseValue=");
        builder.append(baseValue);
        builder.append(", frequency=");
        builder.append(frequency);
        builder.append(", specialAuthorization=");
        builder.append(specialAuthorization);
        builder.append(", previousAudit=");
        builder.append(previousAudit);
        builder.append(", specialty=");
        builder.append(specialty);
        builder.append(", maxAge=");
        builder.append(maxAge);
        builder.append(", minAge=");
        builder.append(minAge);
        builder.append("]");
        return builder.toString();
    }

}
