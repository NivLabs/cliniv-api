package br.com.nivlabs.gp.models.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.GenderIdentity;
import br.com.nivlabs.gp.models.BaseObjectWithId;

/**
 * Classe Person.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Entity
@Table(name = "PESSOA_FISICA")
public class Person extends BaseObjectWithId {

    private static final long serialVersionUID = -3719485861961903955L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_COMPLETO")
    private String fullName;

    @Column(name = "NOME_SOCIAL")
    private String socialName;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "SEXO")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "IDENTIDADE_GENERO")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @Column(name = "NOME_COMP_PAI")
    private String fatherName;

    @Column(name = "NOME_COMP_MAE")
    private String motherName;

    @Column(name = "DATA_NASCIMENTO")
    private Date bornDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
    private PersonAddress address;

    @Column(name = "TELEFONE_PRINCIPAL")
    private String principalNumber;

    @Column(name = "TELEFONE_SECUNDARIO")
    private String secondaryNumber;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private UserApplication user;

    @Column(name = "FOTO")
    @Lob
    private String profilePhoto;

    @Column(name = "EMAIL")
    private String email;

    public Person() {
        super();
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public GenderIdentity getGenderIdentity() {
        return genderIdentity;
    }

    public void setGenderIdentity(GenderIdentity genderIdentity) {
        this.genderIdentity = genderIdentity;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public PersonAddress getAddress() {
        return address;
    }

    public void setAddress(PersonAddress address) {
        this.address = address;
    }

    public String getPrincipalNumber() {
        return principalNumber;
    }

    public void setPrincipalNumber(String principalNumber) {
        this.principalNumber = principalNumber;
    }

    public String getSecondaryNumber() {
        return secondaryNumber;
    }

    public void setSecondaryNumber(String secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    public UserApplication getUser() {
        return user;
    }

    public void setUser(UserApplication user) {
        this.user = user;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((bornDate == null) ? 0 : bornDate.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((genderIdentity == null) ? 0 : genderIdentity.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((motherName == null) ? 0 : motherName.hashCode());
        result = prime * result + ((principalNumber == null) ? 0 : principalNumber.hashCode());
        result = prime * result + ((profilePhoto == null) ? 0 : profilePhoto.hashCode());
        result = prime * result + ((secondaryNumber == null) ? 0 : secondaryNumber.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        Person other = (Person) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
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
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (fatherName == null) {
            if (other.fatherName != null)
                return false;
        } else if (!fatherName.equals(other.fatherName))
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (gender != other.gender)
            return false;
        if (genderIdentity != other.genderIdentity)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (motherName == null) {
            if (other.motherName != null)
                return false;
        } else if (!motherName.equals(other.motherName))
            return false;
        if (principalNumber == null) {
            if (other.principalNumber != null)
                return false;
        } else if (!principalNumber.equals(other.principalNumber))
            return false;
        if (profilePhoto == null) {
            if (other.profilePhoto != null)
                return false;
        } else if (!profilePhoto.equals(other.profilePhoto))
            return false;
        if (secondaryNumber == null) {
            if (other.secondaryNumber != null)
                return false;
        } else if (!secondaryNumber.equals(other.secondaryNumber))
            return false;
        if (socialName == null) {
            if (other.socialName != null)
                return false;
        } else if (!socialName.equals(other.socialName))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", fullName=" + fullName + ", socialName=" + socialName + ", cpf=" + cpf + ", gender=" + gender
                + ", genderIdentity=" + genderIdentity + ", fatherName=" + fatherName + ", motherName=" + motherName + ", bornDate="
                + bornDate + ", address=" + address + ", principalNumber=" + principalNumber + ", secondaryNumber=" + secondaryNumber
                + ", user=" + user + ", profilePhoto=" + profilePhoto + ", email=" + email + "]";
    }

}
