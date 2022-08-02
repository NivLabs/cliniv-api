package br.com.nivlabs.cliniv.models.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumo de Procedimento")
public class ProcedureDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4991985626952633251L;

    @Schema(description = "Identificador do procedimento")
    private Long id;

    @Schema(description = "Descrição do procedimento")
    @NotNull(message = "O(A) nome/descrição do procedimento é obrigatório(a)")
    private String description;

    @Schema(description = "Valor base do procedimento")
    private BigDecimal baseValue;

    @Schema(description = "Periodicidade")
    private String frequency;

    @Schema(description = "Requer autorização especial")
    private boolean specialAuthorization;

    /**
     * Construtor padrão para paginação
     * 
     * @param id
     * @param description
     * @param baseValue
     * @param frequency
     * @param specialAuthorization
     */
    public ProcedureDTO(Long id, String description, BigDecimal baseValue, String frequency, boolean specialAuthorization) {
        super();
        this.id = id;
        this.description = description;
        this.baseValue = baseValue;
        this.frequency = frequency;
        this.specialAuthorization = specialAuthorization;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProcedureDTO [id=");
        builder.append(id);
        builder.append(", description=");
        builder.append(description);
        builder.append(", baseValue=");
        builder.append(baseValue);
        builder.append(", frequency=");
        builder.append(frequency);
        builder.append(", specialAuthorization=");
        builder.append(specialAuthorization);
        builder.append("]");
        return builder.toString();
    }

}