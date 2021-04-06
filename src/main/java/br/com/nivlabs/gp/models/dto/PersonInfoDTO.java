package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.GenderIdentity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe PersonInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@ApiModel(description = "Informações detalhadas da pessoa")
public abstract class PersonInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4735834196671409605L;

    @ApiModelProperty("Identificadfor único")
    private Long id;

    @ApiModelProperty("Nome completo da pessoa")
    @NotNull(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 45, message = "O nome completo é obrigatório")
    private String fullName;

    @ApiModelProperty("Nome social da pessoa")
    private String socialName;

    @ApiModelProperty("Data de nascimento da pessoa")
    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    @ApiModelProperty("Documento da pessoa")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    @ApiModelProperty("Sexo")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty("Identidade de gênero")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @ApiModelProperty("Nome do pai")
    private String fatherName;

    @ApiModelProperty("Nome da mãe")
    private String motherName;

    @ApiModelProperty("Número de telefone|celular principal")
    private String principalNumber;

    @ApiModelProperty("Número de telefone|celular segundário")
    private String secondaryNumber;

    @ApiModelProperty("Endereço")
    private AddressDTO address;

    @ApiModelProperty("Email")
    private String email;

    @ApiModelProperty("Foto do perfil")
    private String profilePhoto;

    private List<DocumentDTO> documents;

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

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "PersonInfoDTO [id=" + id + ", fullName=" + fullName + ", socialName=" + socialName + ", bornDate=" + bornDate
                + ", document=" + document + ", gender=" + gender + ", genderIdentity=" + genderIdentity + ", fatherName=" + fatherName
                + ", motherName=" + motherName + ", principalNumber=" + principalNumber + ", secondaryNumber=" + secondaryNumber
                + ", address=" + address + ", email=" + email + ", profilePhoto=" + profilePhoto + ", documents=" + documents + "]";
    }

}
