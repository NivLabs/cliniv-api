package br.com.nivlabs.cliniv.integration.gtiss;

import br.com.nivlabs.cliniv.integration.RestClientObject;

/**
 * Objeto de Materiais da ANS
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 *
 */

public class Material extends RestClientObject {

    private static final long serialVersionUID = 1885369994400553200L;

    private String id;
    private String code;
    private String description;
    private String model;
    private String anvisaRegistration;
    private String riskClass;
    private String technicalDescription;
    private String manufacturer;
    private String expirationStartDate;
    private String expirationDate;
    private String implantationEndDate;

    public Material() {
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAnvisaRegistration() {
        return anvisaRegistration;
    }

    public void setAnvisaRegistration(String anvisaRegistration) {
        this.anvisaRegistration = anvisaRegistration;
    }

    public String getRiskClass() {
        return riskClass;
    }

    public void setRiskClass(String riskClass) {
        this.riskClass = riskClass;
    }

    public String getTechnicalDescription() {
        return technicalDescription;
    }

    public void setTechnicalDescription(String technicalDescription) {
        this.technicalDescription = technicalDescription;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((riskClass == null) ? 0 : riskClass.hashCode());
        result = prime * result + ((technicalDescription == null) ? 0 : technicalDescription.hashCode());
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
        Material other = (Material) obj;
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
        if (manufacturer == null) {
            if (other.manufacturer != null)
                return false;
        } else if (!manufacturer.equals(other.manufacturer))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (riskClass == null) {
            if (other.riskClass != null)
                return false;
        } else if (!riskClass.equals(other.riskClass))
            return false;
        if (technicalDescription == null) {
            if (other.technicalDescription != null)
                return false;
        } else if (!technicalDescription.equals(other.technicalDescription))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Material [id=" + id + ", code=" + code + ", description=" + description + ", model=" + model + ", anvisaRegistration="
                + anvisaRegistration + ", riskClass=" + riskClass + ", technicalDescription=" + technicalDescription + ", manufacturer="
                + manufacturer + ", expirationStartDate=" + expirationStartDate + ", expirationDate=" + expirationDate
                + ", implantationEndDate=" + implantationEndDate + "]";
    }

}
