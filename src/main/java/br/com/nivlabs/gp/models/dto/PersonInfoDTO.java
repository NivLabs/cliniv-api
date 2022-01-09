package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.BloodType;
import br.com.nivlabs.gp.enums.EthnicGroup;
import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.GenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe PersonInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@Schema(description = "Informações detalhadas da pessoa")
public class PersonInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4735834196671409605L;

    @Schema(description = "Identificadfor único")
    private Long id;

    @Schema(description = "Nome completo da pessoa")
    @NotNull(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 45, message = "O nome completo é obrigatório")
    private String fullName;

    @Schema(description = "Nome social da pessoa")
    private String socialName;

    @Schema(description = "Data de nascimento da pessoa")
    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    @Schema(description = "Documento da pessoa")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    @Schema(description = "Sexo")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description = "Identidade de gênero")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @Schema(description = "Nome do pai")
    private String fatherName;

    @Schema(description = "Nome da mãe")
    private String motherName;

    @Schema(description = "Número de telefone|celular principal")
    private String principalNumber;

    @Schema(description = "Número de telefone|celular segundário")
    private String secondaryNumber;

    @Schema(description = "Endereço")
    private AddressDTO address;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Foto do perfil")
    private String profilePhoto;

    private List<DocumentDTO> documents = new ArrayList<>();

    @Schema(description = "Grupo Étnico do paciente")
    @Enumerated(EnumType.STRING)
    private EthnicGroup ethnicGroup;

    @Schema(description = "Tipo sanguíneo do paciente")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Schema(description = "Nacionalidade do paciente")
    private String nationality;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the socialName
     */
    public String getSocialName() {
        return socialName;
    }

    /**
     * @param socialName the socialName to set
     */
    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    /**
     * @return the bornDate
     */
    public LocalDate getBornDate() {
        return bornDate;
    }

    /**
     * @param bornDate the bornDate to set
     */
    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    /**
     * @return the document
     */
    public DocumentDTO getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return the genderIdentity
     */
    public GenderIdentity getGenderIdentity() {
        return genderIdentity;
    }

    /**
     * @param genderIdentity the genderIdentity to set
     */
    public void setGenderIdentity(GenderIdentity genderIdentity) {
        this.genderIdentity = genderIdentity;
    }

    /**
     * @return the fatherName
     */
    public String getFatherName() {
        return fatherName;
    }

    /**
     * @param fatherName the fatherName to set
     */
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    /**
     * @return the motherName
     */
    public String getMotherName() {
        return motherName;
    }

    /**
     * @param motherName the motherName to set
     */
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    /**
     * @return the principalNumber
     */
    public String getPrincipalNumber() {
        return principalNumber;
    }

    /**
     * @param principalNumber the principalNumber to set
     */
    public void setPrincipalNumber(String principalNumber) {
        this.principalNumber = principalNumber;
    }

    /**
     * @return the secondaryNumber
     */
    public String getSecondaryNumber() {
        return secondaryNumber;
    }

    /**
     * @param secondaryNumber the secondaryNumber to set
     */
    public void setSecondaryNumber(String secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    /**
     * @return the address
     */
    public AddressDTO getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the profilePhoto
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * @param profilePhoto the profilePhoto to set
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * @return the documents
     */
    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }

    /**
     * @return the ethnicGroup
     */
    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    /**
     * @param ethnicGroup the ethnicGroup to set
     */
    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    /**
     * @return the bloodType
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * @param bloodType the bloodType to set
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonInfoDTO [id=");
        builder.append(id);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", socialName=");
        builder.append(socialName);
        builder.append(", bornDate=");
        builder.append(bornDate);
        builder.append(", document=");
        builder.append(document);
        builder.append(", gender=");
        builder.append(gender);
        builder.append(", genderIdentity=");
        builder.append(genderIdentity);
        builder.append(", fatherName=");
        builder.append(fatherName);
        builder.append(", motherName=");
        builder.append(motherName);
        builder.append(", principalNumber=");
        builder.append(principalNumber);
        builder.append(", secondaryNumber=");
        builder.append(secondaryNumber);
        builder.append(", address=");
        builder.append(address);
        builder.append(", email=");
        builder.append(email);
        builder.append(", profilePhoto=");
        builder.append(profilePhoto);
        builder.append(", documents=");
        builder.append(documents);
        builder.append(", ethnicGroup=");
        builder.append(ethnicGroup);
        builder.append(", bloodType=");
        builder.append(bloodType);
        builder.append(", nationality=");
        builder.append(nationality);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, bloodType, bornDate, document, documents, email, ethnicGroup, fatherName, fullName, gender,
                            genderIdentity, id, motherName, nationality, principalNumber, profilePhoto, secondaryNumber, socialName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonInfoDTO other = (PersonInfoDTO) obj;
        return Objects.equals(address, other.address) && bloodType == other.bloodType && Objects.equals(bornDate, other.bornDate)
                && Objects.equals(document, other.document) && Objects.equals(documents, other.documents)
                && Objects.equals(email, other.email) && ethnicGroup == other.ethnicGroup && Objects.equals(fatherName, other.fatherName)
                && Objects.equals(fullName, other.fullName) && gender == other.gender && genderIdentity == other.genderIdentity
                && Objects.equals(id, other.id) && Objects.equals(motherName, other.motherName)
                && Objects.equals(nationality, other.nationality) && Objects.equals(principalNumber, other.principalNumber)
                && Objects.equals(profilePhoto, other.profilePhoto) && Objects.equals(secondaryNumber, other.secondaryNumber)
                && Objects.equals(socialName, other.socialName);
    }

}
