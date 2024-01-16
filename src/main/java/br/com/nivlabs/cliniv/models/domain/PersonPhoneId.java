package br.com.nivlabs.cliniv.models.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe PersonPhoneId.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 3 de nov de 2019
 */
public class PersonPhoneId implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonPhoneId that = (PersonPhoneId) o;
        return Objects.equals(personId, that.personId) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, phoneNumber);
    }

    @Override
    public String toString() {
        return "PersonPhoneId{" +
                "personId=" + personId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
