package br.com.nivlabs.cliniv.integration.gtiss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.nivlabs.cliniv.integration.RestClientObject;

/**
 * Objeto de Medicamentos da ANS
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Medicine extends RestClientObject {

    private static final long serialVersionUID = -2269808406417071745L;
    private String id;
    private String code;
    private String description;
    private String anvisaRegistration;
    private String presentation;
    private String laboratory;
    private String expirationStartDate;
    private String expirationDate;
    private String implantationEndDate;

    public Medicine() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExpirationStartDate() {
        return expirationStartDate;
    }

    public void setExpirationStartDate(String expirationStartDate) {
        this.expirationStartDate = expirationStartDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getImplantationEndDate() {
        return implantationEndDate;
    }

    public void setImplantationEndDate(String implantationEndDate) {
        this.implantationEndDate = implantationEndDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anvisaRegistration == null) ? 0 : anvisaRegistration.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result + ((expirationStartDate == null) ? 0 : expirationStartDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((implantationEndDate == null) ? 0 : implantationEndDate.hashCode());
        result = prime * result + ((laboratory == null) ? 0 : laboratory.hashCode());
        result = prime * result + ((presentation == null) ? 0 : presentation.hashCode());
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
        Medicine other = (Medicine) obj;
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
        if (presentation == null) {
            if (other.presentation != null)
                return false;
        } else if (!presentation.equals(other.presentation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Medicine [id=" + id + ", code=" + code + ", description=" + description + ", anvisaRegistration=" + anvisaRegistration
                + ", presentation=" + presentation + ", laboratory=" + laboratory + ", expirationStartDate=" + expirationStartDate
                + ", expirationDate=" + expirationDate + ", implantationEndDate=" + implantationEndDate + "]";
    }

}
