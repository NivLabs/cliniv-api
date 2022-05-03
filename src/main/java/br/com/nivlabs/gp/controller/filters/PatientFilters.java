package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.enums.PatientType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

/**
 * Filtro de pesquisa dinâmica para paciente
 * 
 * @author viniciosarodrigues
 *
 */
public class PatientFilters extends CustomFilters {

    private static final long serialVersionUID = 1647722811566982336L;

    private String id;

    private String cpf;

    private String fullName = "";

    private String socialName = "";

    private String cnsNumber = "";

    private PatientType type;

    public String getFullName() {
        return "%".concat(fullName).concat("%");
    }

    public String getSocialName() {
        return "%".concat(socialName).concat("%");
    }

    public String getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCnsNumber() {
        return cnsNumber;
    }

    public PatientType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public void setCnsNumber(String cnsNumber) {
        this.cnsNumber = cnsNumber;
    }

    public void setType(PatientType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PatientFilters [id=");
        builder.append(id);
        builder.append(", cpf=");
        builder.append(cpf);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", socialName=");
        builder.append(socialName);
        builder.append(", cnsNumber=");
        builder.append(cnsNumber);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }

}
