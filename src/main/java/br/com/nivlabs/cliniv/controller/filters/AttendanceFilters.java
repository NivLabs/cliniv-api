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
    private String profissionalId;
    private ActiveType activeType;
    private PatientType patientType;
    private EntryType entryType;

    public ActiveType getActiveType() {
        if (activeType == null)
            this.activeType = ActiveType.ALL;
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

    public String getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(String profissionalId) {
        this.profissionalId = profissionalId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AttendanceFilters [sectorId=");
        builder.append(sectorId);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", socialName=");
        builder.append(socialName);
        builder.append(", cpf=");
        builder.append(cpf);
        builder.append(", profissionalId=");
        builder.append(profissionalId);
        builder.append(", activeType=");
        builder.append(activeType);
        builder.append(", patientType=");
        builder.append(patientType);
        builder.append(", entryType=");
        builder.append(entryType);
        builder.append("]");
        return builder.toString();
    }

}
