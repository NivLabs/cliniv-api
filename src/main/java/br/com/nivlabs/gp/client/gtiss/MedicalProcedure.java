package br.com.nivlabs.gp.client.gtiss;

import br.com.nivlabs.gp.client.RestClientObject;

/**
 * Objeto de Procedimentos da ANS
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 *
 */

public class MedicalProcedure extends RestClientObject {

    private static final long serialVersionUID = -5462892795035021367L;

    private String id;
    private String code;
    private String description;
    private String detail;
    private String expirationStartDate;
    private String expirationDate;
    private String implantationEndDate;

    public MedicalProcedure() {
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
        MedicalProcedure other = (MedicalProcedure) obj;
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
        return "MedicalProcedure [id=" + id + ", code=" + code + ", description=" + description + ", detail=" + detail
                + ", expirationStartDate=" + expirationStartDate + ", expirationDate=" + expirationDate + ", implantationEndDate="
                + implantationEndDate + "]";
    }

}
