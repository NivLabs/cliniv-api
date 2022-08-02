package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe MedicineDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 5 de dez de 2019
 */

@Schema(description = "Informações de medicamento")
public class MedicineInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -1687147074910399813L;

    private Long id;

    private String code;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime datetime;

    private String description;

    private String amount;

    private String prescriptionOfficer;

    private String responsibleForTheAdministration;

    private String anvisaRegistration;

    private String presentation;

    private String laboratory;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime expirationStartDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime expirationDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime implantationEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrescriptionOfficer() {
        return prescriptionOfficer;
    }

    public void setPrescriptionOfficer(String prescriptionOfficer) {
        this.prescriptionOfficer = prescriptionOfficer;
    }

    public String getResponsibleForTheAdministration() {
        return responsibleForTheAdministration;
    }

    public void setResponsibleForTheAdministration(String responsibleForTheAdministration) {
        this.responsibleForTheAdministration = responsibleForTheAdministration;
    }

    public String getAnvisaRegistration() {
        return anvisaRegistration;
    }

    public void setAnvisaRegistration(String anvisaRegistration) {
        this.anvisaRegistration = anvisaRegistration;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public LocalDateTime getExpirationStartDate() {
        return expirationStartDate;
    }

    public void setExpirationStartDate(LocalDateTime expirationStartDate) {
        this.expirationStartDate = expirationStartDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getImplantationEndDate() {
        return implantationEndDate;
    }

    public void setImplantationEndDate(LocalDateTime implantationEndDate) {
        this.implantationEndDate = implantationEndDate;
    }

    @Override
    public String toString() {
        return "MedicineInfoDTO [id=" + id + ", code=" + code + ", datetime=" + datetime + ", description="
                + description + ", amount=" + amount + ", prescriptionOfficer=" + prescriptionOfficer
                + ", responsibleForTheAdministration=" + responsibleForTheAdministration + ", anvisaRegistration="
                + anvisaRegistration + ", presentation=" + presentation + ", laboratory=" + laboratory
                + ", expirationStartDate=" + expirationStartDate + ", expirationDate=" + expirationDate
                + ", implantationEndDate=" + implantationEndDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((anvisaRegistration == null) ? 0 : anvisaRegistration.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result + ((expirationStartDate == null) ? 0 : expirationStartDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((implantationEndDate == null) ? 0 : implantationEndDate.hashCode());
        result = prime * result + ((laboratory == null) ? 0 : laboratory.hashCode());
        result = prime * result + ((prescriptionOfficer == null) ? 0 : prescriptionOfficer.hashCode());
        result = prime * result + ((presentation == null) ? 0 : presentation.hashCode());
        result = prime * result
                + ((responsibleForTheAdministration == null) ? 0 : responsibleForTheAdministration.hashCode());
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
        MedicineInfoDTO other = (MedicineInfoDTO) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (anvisaRegistration == null) {
            if (other.anvisaRegistration != null)
                return false;
        } else if (!anvisaRegistration.equals(other.anvisaRegistration))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (datetime == null) {
            if (other.datetime != null)
                return false;
        } else if (!datetime.equals(other.datetime))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (expirationDate == null) {
            if (other.expirationDate != null)
                return false;
        } else if (!expirationDate.equals(other.expirationDate))
            return false;
        if (expirationStartDate == null) {
            if (other.expirationStartDate != null)
                return false;
        } else if (!expirationStartDate.equals(other.expirationStartDate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (implantationEndDate == null) {
            if (other.implantationEndDate != null)
                return false;
        } else if (!implantationEndDate.equals(other.implantationEndDate))
            return false;
        if (laboratory == null) {
            if (other.laboratory != null)
                return false;
        } else if (!laboratory.equals(other.laboratory))
            return false;
        if (prescriptionOfficer == null) {
            if (other.prescriptionOfficer != null)
                return false;
        } else if (!prescriptionOfficer.equals(other.prescriptionOfficer))
            return false;
        if (presentation == null) {
            if (other.presentation != null)
                return false;
        } else if (!presentation.equals(other.presentation))
            return false;
        if (responsibleForTheAdministration == null) {
            if (other.responsibleForTheAdministration != null)
                return false;
        } else if (!responsibleForTheAdministration.equals(other.responsibleForTheAdministration))
            return false;
        return true;
    }
}
