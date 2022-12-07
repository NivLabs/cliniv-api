package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.cliniv.enums.AttendanceLevel;
import br.com.nivlabs.cliniv.enums.BloodType;
import br.com.nivlabs.cliniv.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe que representa um prontuário médico
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 11 de out de 2019
 */
@Schema(description = "Prontuário")
public class MedicalRecordDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6838739544914003033L;

    @Schema(description = "Identificador único do atendimento")
    private Long id;

    @Schema(description = "Data/Hora de entrada do paciente no atendimento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime entryDateTime;

    @Schema(description = "Motivo da entrada")
    private String reasonForEntry;

    @Schema(description = "Data/Hora de alta do paciente")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime exitDateTime;

    @Schema(description = "Identificador do paciente")
    private Long patientId;

    @Schema(description = "Documento do paciente")
    private DocumentDTO document;

    @Schema(description = "Identificador da anmnese")
    private Long anamnesisDigitalDocuentId;

    @Schema(description = "Nome completo do paciente")
    private String fullName;

    @Schema(description = "Tipo sanguíneo do paciente")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Schema(description = "Nome social do pacienteV")
    private String socialName;

    @Schema(description = "Número de Telfone/Celular do paciente")
    private String principalNumber;

    @Schema(description = "CNS")
    private String cnsNumber;

    @Schema(description = "Data de nascimento")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate bornDate;

    @Schema(description = "Acomodação")
    private AccommodationDTO lastAccommodation;

    @Schema(description = "Profissional")
    private ResponsibleDTO lastProfessional;

    @Schema(description = "Sexo")
    private Gender gender;

    @Schema(description = "Eventos")
    private List<AttendanceEventDTO> events = new ArrayList<>();

    @Schema(description = "Medicamentos")
    private List<MedicineInfoDTO> medicines = new ArrayList<>();

    @Schema(description = "Evoluções")
    private List<EvolutionInfoDTO> evolutions = new ArrayList<>();

    @Schema(description = "Alergias")
    private List<String> allergies = new ArrayList<>();

    @Schema(description = "Nível de Risco de atendimento")
    private AttendanceLevel attendanceLevel;

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
     * @return the entryDateTime
     */
    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    /**
     * @param entryDateTime the entryDateTime to set
     */
    public void setEntryDateTime(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    /**
     * @return the reasonForEntry
     */
    public String getReasonForEntry() {
        return reasonForEntry;
    }

    /**
     * @param reasonForEntry the reasonForEntry to set
     */
    public void setReasonForEntry(String reasonForEntry) {
        this.reasonForEntry = reasonForEntry;
    }

    /**
     * @return the exitDateTime
     */
    public LocalDateTime getExitDateTime() {
        return exitDateTime;
    }

    /**
     * @param exitDateTime the exitDateTime to set
     */
    public void setExitDateTime(LocalDateTime exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    /**
     * @return the patientId
     */
    public Long getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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
     * @return the anamnesisDigitalDocuentId
     */
    public Long getAnamnesisDigitalDocuentId() {
        return anamnesisDigitalDocuentId;
    }

    /**
     * @param anamnesisDigitalDocuentId the anamnesisDigitalDocuentId to set
     */
    public void setAnamnesisDigitalDocuentId(Long anamnesisDigitalDocuentId) {
        this.anamnesisDigitalDocuentId = anamnesisDigitalDocuentId;
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
     * @return the lastAccommodation
     */
    public AccommodationDTO getLastAccommodation() {
        return lastAccommodation;
    }

    /**
     * @param lastAccommodation the lastAccommodation to set
     */
    public void setLastAccommodation(AccommodationDTO lastAccommodation) {
        this.lastAccommodation = lastAccommodation;
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
     * @return the events
     */
    public List<AttendanceEventDTO> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<AttendanceEventDTO> events) {
        this.events = events;
    }

    /**
     * @return the medicines
     */
    public List<MedicineInfoDTO> getMedicines() {
        return medicines;
    }

    /**
     * @param medicines the medicines to set
     */
    public void setMedicines(List<MedicineInfoDTO> medicines) {
        this.medicines = medicines;
    }

    /**
     * @return the evolutions
     */
    public List<EvolutionInfoDTO> getEvolutions() {
        return evolutions;
    }

    /**
     * @param evolutions the evolutions to set
     */
    public void setEvolutions(List<EvolutionInfoDTO> evolutions) {
        this.evolutions = evolutions;
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
     * @return the attendanceLevel
     */
    public AttendanceLevel getAttendanceLevel() {
        return attendanceLevel;
    }

    /**
     * @param attendanceLevel the attendanceLevel to set
     */
    public void setAttendanceLevel(AttendanceLevel attendanceLevel) {
        this.attendanceLevel = attendanceLevel;
    }

    public ResponsibleDTO getLastProfessional() {
        return lastProfessional;
    }

    public void setLastProfessional(ResponsibleDTO lastProfessional) {
        this.lastProfessional = lastProfessional;
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
        builder.append(", cnsNumber=");
        builder.append(cnsNumber);
        builder.append(", bornDate=");
        builder.append(bornDate);
        builder.append(", lastAccommodation=");
        builder.append(lastAccommodation);
        builder.append(", lastProfessional=");
        builder.append(lastProfessional);
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
