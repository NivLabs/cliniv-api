package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe MaterialDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 22 de jul de 2020
 */
@Schema(description = "Materiais")
public class MaterialDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -906864967600182992L;

    @Schema(description = "Identificador único do material")
    private String id;

    @Schema(description = "Código do material")
    private String code;

    @Schema(description = "Descrição do material")
    private String description;

    @Schema(description = "Modelo")
    private String model;

    @Schema(description = "Registro da ANVISA")
    private String anvisaRegistration;

    @Schema(description = "Classe de risco")
    private String riskClass;

    @Schema(description = "Descrição técnica")
    private String technicalDescription;

    @Schema(description = "Fornecedor")
    private String manufacturer;

    @Schema(description = "Data início vigência")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime expirationStartDate;

    @Schema(description = "Data fim vigência")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime expirationDate;

    @Schema(description = "Data fim de implantação")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime implantationEndDate;

    public MaterialDTO() {
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
        MaterialDTO other = (MaterialDTO) obj;
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
        return "MaterialDTO [id=" + id + ", code=" + code + ", description=" + description + ", model=" + model + ", anvisaRegistration="
                + anvisaRegistration + ", riskClass=" + riskClass + ", technicalDescription=" + technicalDescription + ", manufacturer="
                + manufacturer + ", expirationStartDate=" + expirationStartDate + ", expirationDate=" + expirationDate
                + ", implantationEndDate=" + implantationEndDate + "]";
    }

}
