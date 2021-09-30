package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;

/**
 * Registro profissional do responsável de saúde (CRM, CRO, CRP, COREN, etc...)
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "Identidade Profissional")
public class ProfessionalIdentityDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5712585514927819479L;

    private String registerType;

    private String registerValue;

    public ProfessionalIdentityDTO() {
        super();
    }

    public ProfessionalIdentityDTO(String registerType, String registerValue) {
        super();
        this.registerType = registerType;
        this.registerValue = registerValue;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getRegisterValue() {
        return registerValue;
    }

    public void setRegisterValue(String registerValue) {
        this.registerValue = registerValue;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProfessionalIdentityDTO [registerType=");
        builder.append(registerType);
        builder.append(", registerValue=");
        builder.append(registerValue);
        builder.append("]");
        return builder.toString();
    }

}
