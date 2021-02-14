package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.AttendanceLevel;
import br.com.nivlabs.gp.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe que representa um prontuário médico
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 11 de out de 2019
 */
@ApiModel(description = "Prontuário")
public class MedicalRecordDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6838739544914003033L;

    @ApiModelProperty("Identificador único do atendimento")
    private Long id;

    @ApiModelProperty("Data/Hora de entrada do paciente no atendimento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime entryDateTime;

    @ApiModelProperty("Motivo da entrada")
    private String reasonForEntry;

    @ApiModelProperty("Data/Hora de alta do paciente")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime exitDateTime;

    @ApiModelProperty("Identificador do paciente")
    private Long patientId;

    @ApiModelProperty("Documento do paciente")
    private DocumentDTO document;

    @ApiModelProperty("Identificador da anmnese")
    private Long anamnesisDigitalDocuentId;

    @ApiModelProperty("Nome completo do paciente")
    private String fullName;

    @ApiModelProperty("Nome social do pacienteV")
    private String socialName;

    @ApiModelProperty("Número de Telfone/Celular do paciente")
    private String principalNumber;

    @ApiModelProperty("Número do SUS")
    private String susNumber;

    @ApiModelProperty("Data de nascimento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    @ApiModelProperty("Acomodação")
    private AccommodationDTO lastAccommodation;

    @ApiModelProperty("Sexo")
    private Gender gender;

    @ApiModelProperty("Eventos")
    private List<AttendanceEventDTO> events = new ArrayList<>();

    @ApiModelProperty("Medicamentos")
    private List<MedicineInfoDTO> medicines = new ArrayList<>();

    @ApiModelProperty("Evoluções")
    private List<EvolutionInfoDTO> evolutions = new ArrayList<>();

    @ApiModelProperty("Alergias")
    private List<String> allergies = new ArrayList<>();

    @ApiModelProperty("Nível de Risco de atendimento")
    private AttendanceLevel attendanceLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public LocalDateTime getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(LocalDateTime exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    public Long getAnamnesisDigitalDocuentId() {
        return anamnesisDigitalDocuentId;
    }

    public void setAnamnesisDigitalDocuentId(Long anamnesisDigitalDocuentId) {
        this.anamnesisDigitalDocuentId = anamnesisDigitalDocuentId;
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

    public String getPrincipalNumber() {
        return principalNumber;
    }

    public void setPrincipalNumber(String principalNumber) {
        this.principalNumber = principalNumber;
    }

    public String getSusNumber() {
        return susNumber;
    }

    public void setSusNumber(String susNumber) {
        this.susNumber = susNumber;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public AccommodationDTO getLastAccommodation() {
        return lastAccommodation;
    }

    public void setLastAccommodation(AccommodationDTO lastAccommodation) {
        this.lastAccommodation = lastAccommodation;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<AttendanceEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<AttendanceEventDTO> events) {
        this.events = events;
    }

    public List<MedicineInfoDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineInfoDTO> medicines) {
        this.medicines = medicines;
    }

    public List<EvolutionInfoDTO> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<EvolutionInfoDTO> evolutions) {
        this.evolutions = evolutions;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public AttendanceLevel getAttendanceLevel() {
        return attendanceLevel;
    }

    public void setAttendanceLevel(AttendanceLevel attendanceLevel) {
        this.attendanceLevel = attendanceLevel;
    }

    public String getReasonForEntry() {
        return reasonForEntry;
    }

    public void setReasonForEntry(String reasonForEntry) {
        this.reasonForEntry = reasonForEntry;
    }

    @Override
    public String toString() {
        return "MedicalRecordDTO [id=" + id + ", entryDateTime=" + entryDateTime + ", reasonForEntry=" + reasonForEntry + ", exitDateTime="
                + exitDateTime + ", patientId=" + patientId + ", document=" + document + ", anamnesisDigitalDocuentId="
                + anamnesisDigitalDocuentId + ", fullName=" + fullName + ", socialName=" + socialName + ", principalNumber="
                + principalNumber + ", susNumber=" + susNumber + ", bornDate=" + bornDate + ", lastAccommodation=" + lastAccommodation
                + ", gender=" + gender + ", events=" + events + ", medicines=" + medicines + ", evolutions=" + evolutions + ", allergies="
                + allergies + ", attendanceLevel=" + attendanceLevel + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
        result = prime * result + ((anamnesisDigitalDocuentId == null) ? 0 : anamnesisDigitalDocuentId.hashCode());
        result = prime * result + ((attendanceLevel == null) ? 0 : attendanceLevel.hashCode());
        result = prime * result + ((bornDate == null) ? 0 : bornDate.hashCode());
        result = prime * result + ((document == null) ? 0 : document.hashCode());
        result = prime * result + ((entryDateTime == null) ? 0 : entryDateTime.hashCode());
        result = prime * result + ((events == null) ? 0 : events.hashCode());
        result = prime * result + ((evolutions == null) ? 0 : evolutions.hashCode());
        result = prime * result + ((exitDateTime == null) ? 0 : exitDateTime.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastAccommodation == null) ? 0 : lastAccommodation.hashCode());
        result = prime * result + ((medicines == null) ? 0 : medicines.hashCode());
        result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
        result = prime * result + ((principalNumber == null) ? 0 : principalNumber.hashCode());
        result = prime * result + ((reasonForEntry == null) ? 0 : reasonForEntry.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        result = prime * result + ((susNumber == null) ? 0 : susNumber.hashCode());
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
        MedicalRecordDTO other = (MedicalRecordDTO) obj;
        if (allergies == null) {
            if (other.allergies != null)
                return false;
        } else if (!allergies.equals(other.allergies))
            return false;
        if (anamnesisDigitalDocuentId == null) {
            if (other.anamnesisDigitalDocuentId != null)
                return false;
        } else if (!anamnesisDigitalDocuentId.equals(other.anamnesisDigitalDocuentId))
            return false;
        if (attendanceLevel != other.attendanceLevel)
            return false;
        if (bornDate == null) {
            if (other.bornDate != null)
                return false;
        } else if (!bornDate.equals(other.bornDate))
            return false;
        if (document == null) {
            if (other.document != null)
                return false;
        } else if (!document.equals(other.document))
            return false;
        if (entryDateTime == null) {
            if (other.entryDateTime != null)
                return false;
        } else if (!entryDateTime.equals(other.entryDateTime))
            return false;
        if (events == null) {
            if (other.events != null)
                return false;
        } else if (!events.equals(other.events))
            return false;
        if (evolutions == null) {
            if (other.evolutions != null)
                return false;
        } else if (!evolutions.equals(other.evolutions))
            return false;
        if (exitDateTime == null) {
            if (other.exitDateTime != null)
                return false;
        } else if (!exitDateTime.equals(other.exitDateTime))
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
        if (lastAccommodation == null) {
            if (other.lastAccommodation != null)
                return false;
        } else if (!lastAccommodation.equals(other.lastAccommodation))
            return false;
        if (medicines == null) {
            if (other.medicines != null)
                return false;
        } else if (!medicines.equals(other.medicines))
            return false;
        if (patientId == null) {
            if (other.patientId != null)
                return false;
        } else if (!patientId.equals(other.patientId))
            return false;
        if (principalNumber == null) {
            if (other.principalNumber != null)
                return false;
        } else if (!principalNumber.equals(other.principalNumber))
            return false;
        if (reasonForEntry == null) {
            if (other.reasonForEntry != null)
                return false;
        } else if (!reasonForEntry.equals(other.reasonForEntry))
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
        return true;
    }

}
