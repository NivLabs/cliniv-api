package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.cliniv.enums.BloodType;
import br.com.nivlabs.cliniv.enums.EthnicGroup;
import br.com.nivlabs.cliniv.enums.Gender;
import br.com.nivlabs.cliniv.enums.GenderIdentity;
import br.com.nivlabs.cliniv.enums.PatientType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Informações detalhadas do paciente
 *
 * @author viniciosarodrigues
 * @since 18-09-2021
 */
@Schema(description = "Informações detalhadas do paciente")
public class PatientInfoDTO extends DataTransferObjectBase {

    @Schema(description = "Identificador único do paciente")
    private Long id;

    @Schema(description = "Identificador único de pessoa")
    private Long personId;

    @Schema(description = "Nome completo do paciente")
    @NotNull(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 45, message = "O nome completo é obrigatório")
    private String fullName;

    @Schema(description = "Nome social do paciente")
    private String socialName;

    @Schema(description = "Data de nascimento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    @Schema(description = "Documento do paciente")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    @Schema(description = "Lista de documentos do paciente")
    private List<DocumentDTO> documents = new ArrayList<>();

    @Schema(description = "Gênero (sexo) do paciente")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description = "Identidade de gênero (orientação) do paciente")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @Schema(description = "Nome do pai do paciente")
    private String fatherName;

    @Schema(description = "Nome da mãe do paciente")
    private String motherName;

    @Schema(description = "Número de telefone/celular principal do paciente")
    private String principalNumber;

    @Schema(description = "Número de telefone/celular secundário do paciente")
    private String secondaryNumber;

    @Schema(description = "E-mail do paciente")
    private String email;

    @Schema(description = "Endereço do paciente")
    private AddressDTO address;

    @Schema(description = "Foto de perfil do paciente")
    private String profilePhoto;

    @Schema(description = "Número do SUS do paciente")
    @Size(max = 15, message = "O código do deve conter no máximo 15 dígitos.")
    private String cnsNumber;

    @Schema(description = "Tipo do paciente (Identificado ou não identificado)")
    private PatientType type;

    @Schema(description = "Anotações sobre o paciente")
    @Size(max = 10000, message = "As anotações não devem passar de 300 caracteres")
    private String annotations;

    @Schema(description = "Data de criação do registro")
    private LocalDateTime createdAt;

    @Schema(description = "Alergias do paciente")
    private List<String> allergies = new ArrayList<>();

    @Schema(description = "Histórico de atendimentos do paciente")
    private List<AttendanceDTO> attendanceHistory = new ArrayList<>();

    @Schema(description = "Plano de saúde do paciente")
    private HealthPlanDTO healthPlan;

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
     * @return the personId
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
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
     * @return the cnsNumber
     */
    public String getCnsNumber() {
        return cnsNumber;
    }

    /**
     * @param cnsNumber the cnsNumber to set
     */
    public void setCnsNumber(String cnsNumber) {
        this.cnsNumber = cnsNumber;
    }

    /**
     * @return the type
     */
    public PatientType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(PatientType type) {
        this.type = type;
    }

    /**
     * @return the annotations
     */
    public String getAnnotations() {
        return annotations;
    }

    /**
     * @param annotations the annotations to set
     */
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the allergies
     */
    public List<String> getAllergies() {
        return allergies;
    }

    /**
     * @param allergies the allergies to set
     */
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    /**
     * @return the attendanceHistory
     */
    public List<AttendanceDTO> getAttendanceHistory() {
        return attendanceHistory;
    }

    /**
     * @param attendanceHistory the attendanceHistory to set
     */
    public void setAttendanceHistory(List<AttendanceDTO> attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }

    /**
     * @return the healthPlan
     */
    public HealthPlanDTO getHealthPlan() {
        return healthPlan;
    }

    /**
     * @param healthPlan the healthPlan to set
     */
    public void setHealthPlan(HealthPlanDTO healthPlan) {
        this.healthPlan = healthPlan;
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
        return "PatientInfoDTO{" +
                "id=" + id +
                ", personId=" + personId +
                ", fullName='" + fullName + '\'' +
                ", socialName='" + socialName + '\'' +
                ", bornDate=" + bornDate +
                ", document=" + document +
                ", documents=" + documents +
                ", gender=" + gender +
                ", genderIdentity=" + genderIdentity +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", principalNumber='" + principalNumber + '\'' +
                ", secondaryNumber='" + secondaryNumber + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", cnsNumber='" + cnsNumber + '\'' +
                ", type=" + type +
                ", annotations='" + annotations + '\'' +
                ", createdAt=" + createdAt +
                ", allergies=" + allergies +
                ", attendanceHistory=" + attendanceHistory +
                ", healthPlan=" + healthPlan +
                ", ethnicGroup=" + ethnicGroup +
                ", bloodType=" + bloodType +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
