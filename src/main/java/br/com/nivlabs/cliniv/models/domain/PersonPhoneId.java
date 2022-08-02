package br.com.nivlabs.cliniv.models.domain;

import java.io.Serializable;

/**
 * Classe PersonPhoneId.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de nov de 2019
 */
public class PersonPhoneId implements Serializable {

    private static final long serialVersionUID = 4804913139653467672L;

    private Long personId;

    private String phoneNumber;

    public PersonPhoneId() {
        super();
    }

    public PersonPhoneId(Long personId, String phoneNumber) {
        super();
        this.personId = personId;
        this.phoneNumber = phoneNumber;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((personId == null) ? 0 : personId.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
        PersonPhoneId other = (PersonPhoneId) obj;
        if (personId == null) {
            if (other.personId != null)
                return false;
        } else if (!personId.equals(other.personId))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PersonPhoneId [personId=" + personId + ", phoneNumber=" + phoneNumber + "]";
    }

}
