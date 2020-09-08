package br.com.nivlabs.gp.models.domain.tiss;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "PROCEDIMENTO")
public class Procedure extends BaseObjectWithId {

    private static final long serialVersionUID = -7145671144200832961L;

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

    @Column(name = "ATIVO")
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Procedure other = (Procedure) obj;
        if (active != other.active)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Procedure [id=" + id + ", description=" + description + ", active=" + active + "]";
    }

    @PrePersist
    private void blockPersist() {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED,
                "Não é permitido a inserção de um novo registro na tabela de procedimentos/eventos");
    }
}
