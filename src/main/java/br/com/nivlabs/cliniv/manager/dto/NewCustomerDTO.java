package br.com.nivlabs.cliniv.manager.dto;

import jakarta.validation.constraints.NotNull;

import br.com.nivlabs.cliniv.manager.models.CGCType;
import br.com.nivlabs.cliniv.models.dto.DataTransferObjectBase;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Registra um novo cliente na base de dados
 * 
 * @author viniciosarodrigues
 *
 */
@Schema(description = "Requisição de criação de novo cliente")
public class NewCustomerDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -1497404743405534956L;

    @Schema(description = "Nome da clínica")
    private String clinicName;

    @Schema(description = "Nome do gestor da clínica")
    private String managerName;

    @Schema(description = "Tipo de cadastro geral do contribuinte")
    @NotNull(message = "Tipo de identificação do cadastro é obrigatório")
    private CGCType cgcType;

    @Schema(description = "Valor da identificação do cadastro geral do contribuinte")
    @NotNull(message = "O CPF/CNPJ é obrigatório")
    private String cgc;

    @Schema(description = "Telefone/celular principal de contato")
    @NotNull(message = "O telefone/celular principal é obrigatório")
    private String principalPhone;

    @Schema(description = "E-mail de contato")
    @NotNull(message = "O e-mail é obrigatório")
    private String email;

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public CGCType getCgcType() {
        return cgcType;
    }

    public void setCgcType(CGCType cgcType) {
        this.cgcType = cgcType;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NewCustomerDTO [clinicName=");
        builder.append(clinicName);
        builder.append(", managerName=");
        builder.append(managerName);
        builder.append(", cgcType=");
        builder.append(cgcType);
        builder.append(", cgc=");
        builder.append(cgc);
        builder.append(", principalPhone=");
        builder.append(principalPhone);
        builder.append(", email=");
        builder.append(email);
        builder.append("]");
        return builder.toString();
    }

}
