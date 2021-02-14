package br.com.nivlabs.gp.models.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Procedimento ou Evento")
public class ProcedureDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4991985626952633251L;

    @ApiModelProperty("Identificador do procedimento")
    private Long id;

    @ApiModelProperty("Descriçãod o procedimento")
    private String description;

    @ApiModelProperty("Valor base do procedimento")
    private BigDecimal baseValue;

    @ApiModelProperty("Periodicidade")
    private String frequency;

    @ApiModelProperty("Requer autorização especial")
    private boolean specialAuthorization;

    @ApiModelProperty("Requer auditoria prévia")
    private boolean previousAudit;

    @ApiModelProperty("Requer especialidade")
    private boolean specialty;

    @ApiModelProperty("Idade máxima para o procedimento")
    private String maxAge;

    @ApiModelProperty("Idade mínima para o procedimento")
    private String minAge;

    @ApiModelProperty("Indicativo de atividade do procedimento")
    private boolean active;

    /**
     * Construtor padrão para paginação
     * 
     * @param id
     * @param description
     * @param baseValue
     * @param frequency
     * @param specialAuthorization
     * @param previousAudit
     * @param specialty
     * @param maxAge
     * @param minAge
     * @param active
     */
    public ProcedureDTO(Long id, String description, BigDecimal baseValue, String frequency, boolean specialAuthorization,
            boolean previousAudit, boolean specialty, String maxAge, String minAge, boolean active) {
        super();
        this.id = id;
        this.description = description;
        this.baseValue = baseValue;
        this.frequency = frequency;
        this.specialAuthorization = specialAuthorization;
        this.previousAudit = previousAudit;
        this.specialty = specialty;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.active = active;
    }

    public ProcedureDTO() {
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
        ProcedureDTO other = (ProcedureDTO) obj;
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
        return "ProcedureDTO [id=" + id + ", description=" + description + ", active=" + active + "]";
    }

}