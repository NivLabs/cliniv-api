package br.com.nivlabs.cliniv.controller.filters;

import br.com.nivlabs.cliniv.enums.ActiveType;
import br.com.nivlabs.cliniv.enums.EntryType;
import br.com.nivlabs.cliniv.enums.PatientType;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

/**
 * Filtros para busca paginada de Atendimentos
 * 
 * @author viniciosarodrigues
 *
 */
public class AttendanceFilters extends CustomFilters {

    private static final long serialVersionUID = 4716373803667305120L;
    private String sectorId;
    private String fullName = "";
    private String socialName = "";
    private String cpf;
    private ActiveType activeType;
    private PatientType patientType;
    private EntryType entryType;

    public ActiveType getActiveType() {
        if (activeType == null)
            this.activeType = ActiveType.ACTIVE;
        return this.activeType;
    }

    public String getFullName() {
        return "%".concat(fullName).concat("%");
    }

    public String getSocialName() {
        return "%".concat(socialName).concat("%");
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public PatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientType patientType) {
        this.patientType = patientType;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public void setActiveType(ActiveType activeType) {
        this.activeType = activeType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((activeType == null) ? 0 : activeType.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((patientType == null) ? 0 : patientType.hashCode());
        result = prime * result + ((sectorId == null) ? 0 : sectorId.hashCode());
        result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttendanceFilters other = (AttendanceFilters) obj;
        if (activeType != other.activeType)
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (entryType != other.entryType)
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (patientType != other.patientType)
            return false;
        if (sectorId == null) {
            if (other.sectorId != null)
                return false;
        } else if (!sectorId.equals(other.sectorId))
            return false;
        if (socialName == null) {
            if (other.socialName != null)
                return false;
        } else if (!socialName.equals(other.socialName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttendanceFilters [sectorId=" + sectorId + ", fullName=" + fullName + ", socialName=" + socialName + ", cpf=" + cpf
                + ", activeType=" + activeType + ", patientType=" + patientType + ", entryType=" + entryType + "]";
    }

}
