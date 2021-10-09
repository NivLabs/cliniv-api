package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.Gender;
import io.swagger.annotations.ApiModel;

/**
 * Classe PersonDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */

@ApiModel(description = "Pessoa")
public abstract class PersonDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -1442279002298984040L;

    private Long id;

    private String fullName;

    private String socialName;

    private String cpf;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    private String principalNumber;

    private Gender gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getPrincipalNumber() {
        return principalNumber;
    }

    public void setPrincipalNumber(String principalNumber) {
        this.principalNumber = principalNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bornDate == null) ? 0 : bornDate.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((principalNumber == null) ? 0 : principalNumber.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
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
        PersonDTO other = (PersonDTO) obj;
        if (bornDate == null) {
            if (other.bornDate != null)
                return false;
        } else if (!bornDate.equals(other.bornDate))
            return false;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (principalNumber == null) {
            if (other.principalNumber != null)
                return false;
        } else if (!principalNumber.equals(other.principalNumber))
            return false;
        if (socialName == null) {
            if (other.socialName != null)
                return false;
        } else if (!socialName.equals(other.socialName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonDTO [id=");
        builder.append(id);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", socialName=");
        builder.append(socialName);
        builder.append(", cpf=");
        builder.append(cpf);
        builder.append(", bornDate=");
        builder.append(bornDate);
        builder.append(", principalNumber=");
        builder.append(principalNumber);
        builder.append(", gender=");
        builder.append(gender);
        builder.append("]");
        return builder.toString();
    }

}
