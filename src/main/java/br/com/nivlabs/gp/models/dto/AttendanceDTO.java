package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

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
@ApiModel("Atendimento")
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

    @ApiModelProperty("Código SUS")
    private String susNumber;

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
     * @param susNumber
     * @param sectorDescripton
     * @param level
     */
    public AttendanceDTO(Long id, String fullName, String socialName, LocalDateTime entryDatetime, String entryCause,
            EntryType type, Long patientId, String sectorDescription, String susNumber, AttendanceLevel level) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.socialName = socialName;
        this.entryDatetime = entryDatetime;
        this.entryCause = entryCause;
        this.type = type;
        this.patientId = patientId;
        this.sectorDescription = sectorDescription;
        this.susNumber = susNumber;
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

    public String getSusNumber() {
        return susNumber;
    }

    public void setSusNumber(String susNumber) {
        this.susNumber = susNumber;
    }

    public AttendanceLevel getLevel() {
        return level;
    }

    public void setLevel(AttendanceLevel level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entryCause == null) ? 0 : entryCause.hashCode());
        result = prime * result + ((entryDatetime == null) ? 0 : entryDatetime.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isFinished == null) ? 0 : isFinished.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
        result = prime * result + ((patientType == null) ? 0 : patientType.hashCode());
        result = prime * result + ((sectorDescription == null) ? 0 : sectorDescription.hashCode());
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
        AttendanceDTO other = (AttendanceDTO) obj;
        if (entryCause == null) {
            if (other.entryCause != null)
                return false;
        } else if (!entryCause.equals(other.entryCause))
            return false;
        if (entryDatetime == null) {
            if (other.entryDatetime != null)
                return false;
        } else if (!entryDatetime.equals(other.entryDatetime))
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isFinished == null) {
            if (other.isFinished != null)
                return false;
        } else if (!isFinished.equals(other.isFinished))
            return false;
        if (level != other.level)
            return false;
        if (patientId == null) {
            if (other.patientId != null)
                return false;
        } else if (!patientId.equals(other.patientId))
            return false;
        if (patientType != other.patientType)
            return false;
        if (sectorDescription == null) {
            if (other.sectorDescription != null)
                return false;
        } else if (!sectorDescription.equals(other.sectorDescription))
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

    @Override
    public String toString() {
        return "AttendanceDTO [id=" + id + ", fullName=" + fullName + ", socialName=" + socialName + ", entryDatetime=" + entryDatetime
                + ", entryCause=" + entryCause + ", isFinished=" + isFinished + ", type=" + type + ", patientType=" + patientType
                + ", patientId=" + patientId + ", sectorDescription=" + sectorDescription + ", susNumber=" + susNumber
                + ", level=" + level + "]";
    }

}
