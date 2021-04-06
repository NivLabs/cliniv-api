package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import br.com.nivlabs.gp.enums.PatientType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe NewPatientDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de out de 2019
 */
/**
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "Informações detalhadas do paciente")
public class PatientInfoDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = 1575416178033511932L;

    @ApiModelProperty("Identificador único do paciente")
    private Long id;

    @ApiModelProperty("Identificador único de pessoa")
    private Long personId;

    @ApiModelProperty("Nome completo do paciente")
    @NotNull(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 45, message = "O nome completo é obrigatório")
    private String fullName;

    @ApiModelProperty("Nome social do paciente")
    private String socialName;

    @ApiModelProperty("Data de nascimento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    @ApiModelProperty("Documento do paciente")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    private List<DocumentDTO> documents;

    @ApiModelProperty("Gênero (sexo) do paciente")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty("Identidade de gênero (orientação) do paciente")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @ApiModelProperty("Nome do pai do paciente")
    private String fatherName;

    @ApiModelProperty("Nome da mãe do paciente")
    private String motherName;

    @ApiModelProperty("Número de telefone/celular principal do paciente")
    private String principalNumber;

    @ApiModelProperty("Número de telefone/celular secundário do paciente")
    private String secondaryNumber;

    @ApiModelProperty("Endereço do paciente")
    private AddressDTO address;

    @ApiModelProperty("Foto de perfil do paciente")
    private String profilePhoto;

    @ApiModelProperty("Número do SUS do paciente")
    @Size(max = 15, message = "O código do deve conter no máximo 15 dígitos.")
    private String susNumber;

    @ApiModelProperty("Tipo do paciente (Identificado ou não identificado)")
    private PatientType type;

    @ApiModelProperty("Anotações sobre o paciente")
    @Size(max = 10000, message = "As anotações não devem passar de 300 caracteres")
    private String annotations;

    @ApiModelProperty("Data de criação do registro")
    private LocalDateTime createdAt;

    @ApiModelProperty("Alergias do paciente")
    private List<String> allergies = new ArrayList<>();

    @ApiModelProperty("Histórico de atendimentos do paciente")
    private List<AttendanceDTO> attendanceHistory = new ArrayList<>();

    @ApiModelProperty("Plano de saúde do paciente")
    private HealthPlanDTO healthPlan;

    @ApiModelProperty("Grupo Étnico do paciente")
    @Enumerated(EnumType.STRING)
    private EthnicGroup ethnicGroup;

    @ApiModelProperty("Tipo sanguíneo do paciente")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @ApiModelProperty("Nacionalidade do paciente")
    private String nationality;

    private String dispatcher;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate expeditionDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate validate;

    private String uf;

    public PatientInfoDTO() {
        super();
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getSusNumber() {
        return susNumber;
    }

    public void setSusNumber(String susNumber) {
        this.susNumber = susNumber;
    }

    public PatientType getType() {
        return type;
    }

    public void setType(PatientType type) {
        this.type = type;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public HealthPlanDTO getHealthPlan() {
        return healthPlan;
    }

    public void setHealthPlan(HealthPlanDTO healthPlan) {
        this.healthPlan = healthPlan;
    }

    public List<AttendanceDTO> getAttendanceHistory() {
        return attendanceHistory;
    }

    public void setAttendanceHistory(List<AttendanceDTO> attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
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

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public LocalDate getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(LocalDate expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDTO> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "PatientInfoDTO [id=" + id + ", personId=" + personId + ", fullName=" + fullName + ", socialName=" + socialName
                + ", bornDate=" + bornDate + ", document=" + document + ", documents=" + documents + ", gender=" + gender
                + ", genderIdentity=" + genderIdentity + ", fatherName=" + fatherName + ", motherName=" + motherName + ", principalNumber="
                + principalNumber + ", secondaryNumber=" + secondaryNumber + ", address=" + address + ", profilePhoto=" + profilePhoto
                + ", susNumber=" + susNumber + ", type=" + type + ", annotations=" + annotations + ", createdAt=" + createdAt
                + ", allergies=" + allergies + ", attendanceHistory=" + attendanceHistory + ", healthPlan=" + healthPlan + ", ethnicGroup="
                + ethnicGroup + ", bloodType=" + bloodType + ", nationality=" + nationality + ", dispatcher=" + dispatcher
                + ", expeditionDate=" + expeditionDate + ", validate=" + validate + ", uf=" + uf + "]";
    }

}
