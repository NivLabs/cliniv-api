package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe MedicalProcedureDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 22 de jul de 2020
 */
@ApiModel("Procedimento")
public class MedicalProcedureDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8019585889759791849L;

    @ApiModelProperty("Identificador único do procedimento")
    private String id;

    @ApiModelProperty("Código do procedimento")
    private String code;

    @ApiModelProperty("Descrição do procedimento")
    private String description;

    @ApiModelProperty("Detalhes do procedimento")
    private String detail;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime expirationStartDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime expirationDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime implantationEndDate;

    public MedicalProcedureDTO() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((detail == null) ? 0 : detail.hashCode());
        result = prime * result + ((expirationDate == null) ? 0 : expirationDate.hashCode());
        result = prime * result + ((expirationStartDate == null) ? 0 : expirationStartDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((implantationEndDate == null) ? 0 : implantationEndDate.hashCode());
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
        MedicalProcedureDTO other = (MedicalProcedureDTO) obj;
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
        if (detail == null) {
            if (other.detail != null)
                return false;
        } else if (!detail.equals(other.detail))
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
        return true;
    }

    @Override
    public String toString() {
        return "MedicalProcedureDTO [id=" + id + ", code=" + code + ", description=" + description + ", detail=" + detail
                + ", expirationStartDate=" + expirationStartDate + ", expirationDate=" + expirationDate + ", implantationEndDate="
                + implantationEndDate + "]";
    }

}
