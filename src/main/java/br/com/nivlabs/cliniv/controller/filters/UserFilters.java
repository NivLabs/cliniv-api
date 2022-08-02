package br.com.nivlabs.cliniv.controller.filters;

import br.com.nivlabs.cliniv.enums.Gender;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

/**
 * Filtros para pesquisa de Usuários
 * 
 * @author viniciosarodrigues
 *
 */
public class UserFilters extends CustomFilters {

    private static final long serialVersionUID = 7760736861571277630L;

    private String userName = "";

    private String cpf = "";

    private String fullName = "";

    private String socialName = "";

    private Gender gender;

    public String getUserName() {
        return "%".concat(userName).concat("%");
    }

    public String getCpf() {
        return cpf;
    }

    public String getFullName() {
        return "%".concat(fullName).concat("%");
    }

    public String getSocialName() {
        return "%".concat(socialName).concat("%");
    }

    public Gender getGender() {
        return gender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        UserFilters other = (UserFilters) obj;
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
        if (gender != other.gender)
            return false;
        if (socialName == null) {
            if (other.socialName != null)
                return false;
        } else if (!socialName.equals(other.socialName))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserFilters [userName=" + userName + ", cpf=" + cpf + ", fullName=" + fullName + ", socialName=" + socialName + ", gender="
                + gender + "]";
    }

}
