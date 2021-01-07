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
@ApiModel("Informações detalhadas do paciente")
public class PatientInfoDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = 1575416178033511932L;

    @ApiModelProperty("Identificador único do paciente")
    private Long id;

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

    @ApiModelProperty("Alergias do pacente")
    private List<String> allergies = new ArrayList<>();

    @ApiModelProperty("Histórico de atendimentos do paciente")
    private List<AttendanceDTO> attendanceHistory = new ArrayList<>();

    @ApiModelProperty("Plano de saúde do paciente")
    private HealthPlanDTO healthPlan;

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

    @Override
    public String toString() {
        return "PatientInfoDTO [id=" + id + ", fullName=" + fullName + ", socialName=" + socialName + ", bornDate=" + bornDate
                + ", document=" + document + ", gender=" + gender + ", genderIdentity=" + genderIdentity + ", fatherName=" + fatherName
                + ", motherName=" + motherName + ", principalNumber=" + principalNumber + ", secondaryNumber=" + secondaryNumber
                + ", address=" + address + ", profilePhoto=" + profilePhoto + ", susNumber=" + susNumber + ", type=" + type
                + ", annotations=" + annotations + ", createdAt=" + createdAt + ", allergies=" + allergies + ", attendanceHistory="
                + attendanceHistory + ", healthPlan=" + healthPlan + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
        result = prime * result + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result + ((attendanceHistory == null) ? 0 : attendanceHistory.hashCode());
        result = prime * result + ((bornDate == null) ? 0 : bornDate.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((document == null) ? 0 : document.hashCode());
        result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((genderIdentity == null) ? 0 : genderIdentity.hashCode());
        result = prime * result + ((healthPlan == null) ? 0 : healthPlan.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((motherName == null) ? 0 : motherName.hashCode());
        result = prime * result + ((principalNumber == null) ? 0 : principalNumber.hashCode());
        result = prime * result + ((profilePhoto == null) ? 0 : profilePhoto.hashCode());
        result = prime * result + ((secondaryNumber == null) ? 0 : secondaryNumber.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        result = prime * result + ((susNumber == null) ? 0 : susNumber.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        PatientInfoDTO other = (PatientInfoDTO) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (allergies == null) {
            if (other.allergies != null)
                return false;
        } else if (!allergies.equals(other.allergies))
            return false;
        if (annotations == null) {
            if (other.annotations != null)
                return false;
        } else if (!annotations.equals(other.annotations))
            return false;
        if (attendanceHistory == null) {
            if (other.attendanceHistory != null)
                return false;
        } else if (!attendanceHistory.equals(other.attendanceHistory))
            return false;
        if (bornDate == null) {
            if (other.bornDate != null)
                return false;
        } else if (!bornDate.equals(other.bornDate))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (document == null) {
            if (other.document != null)
                return false;
        } else if (!document.equals(other.document))
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
        if (healthPlan == null) {
            if (other.healthPlan != null)
                return false;
        } else if (!healthPlan.equals(other.healthPlan))
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
        if (susNumber == null) {
            if (other.susNumber != null)
                return false;
        } else if (!susNumber.equals(other.susNumber))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
