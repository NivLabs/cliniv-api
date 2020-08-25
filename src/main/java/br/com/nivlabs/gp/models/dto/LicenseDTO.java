package br.com.nivlabs.gp.models.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Licença de uso")
public class LicenseDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 5513329730873896353L;

    @ApiModelProperty("Chave de acesso à API")
    private String key;

    @ApiModelProperty("Data de início")
    @DateTimeFormat(iso = ISO.DATE)
    private Date startDate;

    @ApiModelProperty("Data fim")
    @DateTimeFormat(iso = ISO.DATE)
    private Date endDate;

    public LicenseDTO() {
        super();
    }

    public LicenseDTO(String key, Date startDate, Date endDate) {
        super();
        this.key = key;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
        LicenseDTO other = (LicenseDTO) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LicenseDTO [key=" + key + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

}
