package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.nivlabs.gp.enums.AttendanceLevel;
import br.com.nivlabs.gp.enums.EntryType;
import br.com.nivlabs.gp.enums.PatientType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe VisitDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de nov de 2019
 */
@ApiModel(description = "Atendimento")
public class AttendanceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -7717106082371494163L;

    @ApiModelProperty("Identificador único do atendimento")
    private Long id;

    @ApiModelProperty("Nome completo")
    private String fullName;

    @ApiModelProperty("Nome Social")
    private String socialName;

    @ApiModelProperty("Data da entrada")
    private LocalDateTime entryDatetime;

    @ApiModelProperty("Causa da Entrada")
    private String entryCause;

    @ApiModelProperty("Retorna true se já estiver sido finalizada")
    private Boolean isFinished;

    @ApiModelProperty("Tipo da entrada")
    private EntryType type;

    @ApiModelProperty("Tipo do paciente")
    private PatientType patientType;

    @ApiModelProperty("Identificador do paciente")
    private Long patientId;

    @ApiModelProperty("Setor atual em que o paciente se encontra")
    private String sectorDescription;

    @ApiModelProperty("Código CNS")
    private String cnsNumber;

    @ApiModelProperty("Nível de risco do paciente do atendimento")
    private AttendanceLevel level;

    /**
     * Construtor usado para paginação
     * 
     * @param id
     * @param fullName
     * @param socialName
     * @param entryDatetime
     * @param entryCause
     * @param isFinished
     * @param type
     * @param patientType
     * @param patientId
     * @param sectorDescription
     * @param cnsNumber
     * @param sectorDescripton
     * @param level
     */
    public AttendanceDTO(Long id, String fullName, String socialName, LocalDateTime entryDatetime, String entryCause,
            EntryType type, Long patientId, String sectorDescription, String cnsNumber, AttendanceLevel level) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.socialName = socialName;
        this.entryDatetime = entryDatetime;
        this.entryCause = entryCause;
        this.type = type;
        this.patientId = patientId;
        this.sectorDescription = sectorDescription;
        this.cnsNumber = cnsNumber;
        this.level = level;
    }

    public AttendanceDTO() {
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

    public LocalDateTime getEntryDatetime() {
        return entryDatetime;
    }

    public void setEntryDatetime(LocalDateTime entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    public String getEntryCause() {
        return entryCause;
    }

    public void setEntryCause(String entryCause) {
        this.entryCause = entryCause;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public PatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientType patientType) {
        this.patientType = patientType;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getSectorDescription() {
        return sectorDescription;
    }

    public void setSectorDescription(String sectorDescription) {
        this.sectorDescription = sectorDescription;
    }

    public String getCnsNumber() {
        return cnsNumber;
    }

    public void setCnsNumber(String cnsNumber) {
        this.cnsNumber = cnsNumber;
    }

    public AttendanceLevel getLevel() {
        return level;
    }

    public void setLevel(AttendanceLevel level) {
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AttendanceDTO [id=");
        builder.append(id);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", socialName=");
        builder.append(socialName);
        builder.append(", entryDatetime=");
        builder.append(entryDatetime);
        builder.append(", entryCause=");
        builder.append(entryCause);
        builder.append(", isFinished=");
        builder.append(isFinished);
        builder.append(", type=");
        builder.append(type);
        builder.append(", patientType=");
        builder.append(patientType);
        builder.append(", patientId=");
        builder.append(patientId);
        builder.append(", sectorDescription=");
        builder.append(sectorDescription);
        builder.append(", cnsNumber=");
        builder.append(cnsNumber);
        builder.append(", level=");
        builder.append(level);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnsNumber, entryCause, entryDatetime, fullName, id, isFinished, level, patientId, patientType,
                            sectorDescription, socialName, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttendanceDTO other = (AttendanceDTO) obj;
        return Objects.equals(cnsNumber, other.cnsNumber) && Objects.equals(entryCause, other.entryCause)
                && Objects.equals(entryDatetime, other.entryDatetime) && Objects.equals(fullName, other.fullName)
                && Objects.equals(id, other.id) && Objects.equals(isFinished, other.isFinished) && level == other.level
                && Objects.equals(patientId, other.patientId) && patientType == other.patientType
                && Objects.equals(sectorDescription, other.sectorDescription) && Objects.equals(socialName, other.socialName)
                && type == other.type;
    }

}
