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

    private String susNumber = "";

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

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSusNumber() {
        return susNumber;
    }

    public void setSusNumber(String susNumber) {
        this.susNumber = susNumber;
    }

    public PatientType getType() {
        return type;
    }

    public void setType(PatientType type) {
        this.type = type;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        result = prime * result + ((susNumber == null) ? 0 : susNumber.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PatientFilters other = (PatientFilters) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (socialName == null) {
            if (other.socialName != null)
                return false;
        } else if (!socialName.equals(other.socialName))
            return false;
        if (susNumber == null) {
            if (other.susNumber != null)
                return false;
        } else if (!susNumber.equals(other.susNumber))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PatientFilters [id=" + id + ", cpf=" + cpf + ", fullName=" + fullName + ", socialName=" + socialName + ", susNumber="
                + susNumber + ", type=" + type + "]";
    }

}
