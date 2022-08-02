package br.com.nivlabs.cliniv.controller.filters;

import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

public class ResponsibleFilters extends CustomFilters {

    private static final long serialVersionUID = -8122726857291316469L;

    private String id;

    private String professionalIdentity = "";

    private String cpf;

    private String fullName = "";

    private String socialName = "";

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

    public String getProfessionalIdentity() {
        return professionalIdentity;
    }

    public void setProfessionalIdentity(String professionalIdentity) {
        this.professionalIdentity = professionalIdentity;
    }

    public String getCpf() {
        return cpf;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResponsibleFilters other = (ResponsibleFilters) obj;
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
        if (professionalIdentity == null) {
            if (other.professionalIdentity != null)
                return false;
        } else if (!professionalIdentity.equals(other.professionalIdentity))
            return false;
        if (socialName == null) {
            if (other.socialName != null)
                return false;
        } else if (!socialName.equals(other.socialName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((professionalIdentity == null) ? 0 : professionalIdentity.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ResponsibleFilters [id=" + id + ", professionalIdentity=" + professionalIdentity + ", cpf=" + cpf + ", fullName=" + fullName
                + ", socialName=" + socialName + "]";
    }

}
