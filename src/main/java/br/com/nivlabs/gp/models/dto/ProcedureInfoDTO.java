package br.com.nivlabs.gp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * @author viniciosarodrigues
 *
 */
@Schema(description = "Informações de Procedimento")
public class ProcedureInfoDTO extends ProcedureDTO {

    private static final long serialVersionUID = -7663943079267996719L;

    @Schema(description = "Requer auditoria prévia")
    private boolean previousAudit;

    @Schema(description = "Requer especialidade")
    private boolean specialty;

    @Schema(description = "Idade máxima para o procedimento")
    private String maxAge;

    @Schema(description = "Idade mínima para o procedimento")
    private String minAge;

    /**
     * @return the previousAudit
     */
    public boolean isPreviousAudit() {
        return previousAudit;
    }

    /**
     * @param previousAudit the previousAudit to set
     */
    public void setPreviousAudit(boolean previousAudit) {
        this.previousAudit = previousAudit;
    }

    /**
     * @return the specialty
     */
    public boolean isSpecialty() {
        return specialty;
    }

    /**
     * @param specialty the specialty to set
     */
    public void setSpecialty(boolean specialty) {
        this.specialty = specialty;
    }

    /**
     * @return the maxAge
     */
    public String getMaxAge() {
        return maxAge;
    }

    /**
     * @param maxAge the maxAge to set
     */
    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * @return the minAge
     */
    public String getMinAge() {
        return minAge;
    }

    /**
     * @param minAge the minAge to set
     */
    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProcedureInfoDTO [previousAudit=");
        builder.append(previousAudit);
        builder.append(", specialty=");
        builder.append(specialty);
        builder.append(", maxAge=");
        builder.append(maxAge);
        builder.append(", minAge=");
        builder.append(minAge);
        builder.append(", getBaseValue()=");
        builder.append(getBaseValue());
        builder.append(", getFrequency()=");
        builder.append(getFrequency());
        builder.append(", isSpecialAuthorization()=");
        builder.append(isSpecialAuthorization());
        builder.append(", getId()=");
        builder.append(getId());
        builder.append(", getDescription()=");
        builder.append(getDescription());
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append(", getClass()=");
        builder.append(getClass());
        builder.append(", hashCode()=");
        builder.append(hashCode());
        builder.append("]");
        return builder.toString();
    }

}
