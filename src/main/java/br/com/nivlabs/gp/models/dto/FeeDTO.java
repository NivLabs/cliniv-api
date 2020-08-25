package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe FeeDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 22 de jul de 2020
 */
@ApiModel("Taxas")
public class FeeDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 139714345053962715L;

    @ApiModelProperty("Identificador único da taxa")
    private String id;

    @ApiModelProperty("Código da taxa")
    private String code;

    @ApiModelProperty("Descrição da taxa")
    private String description;

    @ApiModelProperty("Detalhe")
    private String detail;

    @ApiModelProperty("Início de vigência")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime startOfTerm;

    @ApiModelProperty("Fim de vigência")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime endOfTerm;

    @ApiModelProperty("Data final de implantação")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime endOfImplantationDate;

    public FeeDTO() {
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

    public LocalDateTime getStartOfTerm() {
        return startOfTerm;
    }

    public void setStartOfTerm(LocalDateTime startOfTerm) {
        this.startOfTerm = startOfTerm;
    }

    public LocalDateTime getEndOfTerm() {
        return endOfTerm;
    }

    public void setEndOfTerm(LocalDateTime endOfTerm) {
        this.endOfTerm = endOfTerm;
    }

    public LocalDateTime getEndOfImplantationDate() {
        return endOfImplantationDate;
    }

    public void setEndOfImplantationDate(LocalDateTime endOfImplantationDate) {
        this.endOfImplantationDate = endOfImplantationDate;
    }

    @Override
    public String toString() {
        return "FeeDTO [id=" + id + ", code=" + code + ", description=" + description + ", detail=" + detail + ", startOfTerm="
                + startOfTerm + ", endOfTerm=" + endOfTerm + ", endOfImplantationDate=" + endOfImplantationDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((detail == null) ? 0 : detail.hashCode());
        result = prime * result + ((endOfImplantationDate == null) ? 0 : endOfImplantationDate.hashCode());
        result = prime * result + ((endOfTerm == null) ? 0 : endOfTerm.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((startOfTerm == null) ? 0 : startOfTerm.hashCode());
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
        FeeDTO other = (FeeDTO) obj;
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
        if (endOfImplantationDate == null) {
            if (other.endOfImplantationDate != null)
                return false;
        } else if (!endOfImplantationDate.equals(other.endOfImplantationDate))
            return false;
        if (endOfTerm == null) {
            if (other.endOfTerm != null)
                return false;
        } else if (!endOfTerm.equals(other.endOfTerm))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (startOfTerm == null) {
            if (other.startOfTerm != null)
                return false;
        } else if (!startOfTerm.equals(other.startOfTerm))
            return false;
        return true;
    }

}
