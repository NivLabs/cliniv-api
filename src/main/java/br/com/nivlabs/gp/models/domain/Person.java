package br.com.nivlabs.gp.models.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.BloodType;
import br.com.nivlabs.gp.enums.EthnicGroup;
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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "person")
    private List<PersonDocument> documents = new ArrayList<>();

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
    private LocalDate bornDate;

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

    @Column(name = "GRUPO_ETNICO")
    @Enumerated(EnumType.STRING)
    private EthnicGroup ethnicGroup;

    @Column(name = "TIPO_SANGUINEO")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Column(name = "NACIONALIDADE")
    private String nationality;

    public Person() {
        super();
    }

    public Person(Long id) {
        this.id = id;
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

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
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

    public List<PersonDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<PersonDocument> documents) {
        this.documents = documents;
    }

    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Person ["
                + "id=" + id
                + ", fullName=" + fullName
                + ", socialName=" + socialName
                + ", cpf=" + cpf
                + ", documents=" + documents
                + ", gender=" + gender
                + ", genderIdentity=" + genderIdentity
                + ", fatherName=" + fatherName
                + ", motherName=" + motherName
                + ", bornDate=" + bornDate
                + ", address=" + address
                + ", principalNumber=" + principalNumber
                + ", secondaryNumber=" + secondaryNumber
                + ", user=" + user
                + ", profilePhoto=" + profilePhoto
                + ", email=" + email
                + ", ethnicGroup=" + ethnicGroup
                + ", bloodType=" + bloodType
                + ", nationality=" + nationality
                + "]";
    }

}
