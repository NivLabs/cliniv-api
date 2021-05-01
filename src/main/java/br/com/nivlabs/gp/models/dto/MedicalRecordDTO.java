package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.AttendanceLevel;
import br.com.nivlabs.gp.enums.BloodType;
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

    @ApiModelProperty("Tipo sanguíneo do paciente")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

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
        StringBuilder builder = new StringBuilder();
        builder.append("MedicalRecordDTO [id=");
        builder.append(id);
        builder.append(", entryDateTime=");
        builder.append(entryDateTime);
        builder.append(", reasonForEntry=");
        builder.append(reasonForEntry);
        builder.append(", exitDateTime=");
        builder.append(exitDateTime);
        builder.append(", patientId=");
        builder.append(patientId);
        builder.append(", document=");
        builder.append(document);
        builder.append(", anamnesisDigitalDocuentId=");
        builder.append(anamnesisDigitalDocuentId);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", bloodType=");
        builder.append(bloodType);
        builder.append(", socialName=");
        builder.append(socialName);
        builder.append(", principalNumber=");
        builder.append(principalNumber);
        builder.append(", susNumber=");
        builder.append(susNumber);
        builder.append(", bornDate=");
        builder.append(bornDate);
        builder.append(", lastAccommodation=");
        builder.append(lastAccommodation);
        builder.append(", gender=");
        builder.append(gender);
        builder.append(", events=");
        builder.append(events);
        builder.append(", medicines=");
        builder.append(medicines);
        builder.append(", evolutions=");
        builder.append(evolutions);
        builder.append(", allergies=");
        builder.append(allergies);
        builder.append(", attendanceLevel=");
        builder.append(attendanceLevel);
        builder.append("]");
        return builder.toString();
    }

}
