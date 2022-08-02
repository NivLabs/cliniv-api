package br.com.nivlabs.cliniv.report;

import java.util.Date;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Parâmetros necessários para montar a declaração de comparecimento do paciente
 * 
 * @author viniciosarodrigues
 *
 */
public class AttendanceParams extends ReportParam {

    private static final long serialVersionUID = 5738508744853919737L;

    private String patientName;

    private String documentType;

    private String documentValue;

    private String responsibleName;

    private String responsibleDocumentType;

    private String responsibleDocumentValue;

    @DateTimeFormat(iso = ISO.DATE)
    private Date startHour;

    @DateTimeFormat(iso = ISO.DATE)
    private Date endHour;

    public AttendanceParams() {
        super();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentValue() {
        return documentValue;
    }

    public void setDocumentValue(String documentValue) {
        this.documentValue = documentValue;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getResponsibleDocumentType() {
        return responsibleDocumentType;
    }

    public void setResponsibleDocumentType(String responsibleDocumentType) {
        this.responsibleDocumentType = responsibleDocumentType;
    }

    public String getResponsibleDocumentValue() {
        return responsibleDocumentValue;
    }

    public void setResponsibleDocumentValue(String responsibleDocumentValue) {
        this.responsibleDocumentValue = responsibleDocumentValue;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    @Override
    public Map<String, Object> getParams() {
        super.getParams().put("patientName", this.getPatientName());
        super.getParams().put("documentType", this.getDocumentType());
        super.getParams().put("documentValue", this.getDocumentValue());

        super.getParams().put("responsibleName", this.getResponsibleName());
        super.getParams().put("responsibleDocumentType", this.getResponsibleDocumentType());
        super.getParams().put("responsibleDocumentValue", this.getResponsibleDocumentValue());

        super.getParams().put("startHour", StringUtils.dateFormat(this.getStartHour(), StringUtils.BRASIL_DATE_TIME));
        super.getParams().put("endHour", StringUtils.dateFormat(this.getEndHour(), StringUtils.BRASIL_DATE_TIME));

        return super.getParams();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((documentType == null) ? 0 : documentType.hashCode());
        result = prime * result + ((documentValue == null) ? 0 : documentValue.hashCode());
        result = prime * result + ((endHour == null) ? 0 : endHour.hashCode());
        result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
        result = prime * result + ((responsibleDocumentType == null) ? 0 : responsibleDocumentType.hashCode());
        result = prime * result + ((responsibleDocumentValue == null) ? 0 : responsibleDocumentValue.hashCode());
        result = prime * result + ((responsibleName == null) ? 0 : responsibleName.hashCode());
        result = prime * result + ((startHour == null) ? 0 : startHour.hashCode());
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
        AttendanceParams other = (AttendanceParams) obj;
        if (documentType == null) {
            if (other.documentType != null)
                return false;
        } else if (!documentType.equals(other.documentType))
            return false;
        if (documentValue == null) {
            if (other.documentValue != null)
                return false;
        } else if (!documentValue.equals(other.documentValue))
            return false;
        if (endHour == null) {
            if (other.endHour != null)
                return false;
        } else if (!endHour.equals(other.endHour))
            return false;
        if (patientName == null) {
            if (other.patientName != null)
                return false;
        } else if (!patientName.equals(other.patientName))
            return false;
        if (responsibleDocumentType == null) {
            if (other.responsibleDocumentType != null)
                return false;
        } else if (!responsibleDocumentType.equals(other.responsibleDocumentType))
            return false;
        if (responsibleDocumentValue == null) {
            if (other.responsibleDocumentValue != null)
                return false;
        } else if (!responsibleDocumentValue.equals(other.responsibleDocumentValue))
            return false;
        if (responsibleName == null) {
            if (other.responsibleName != null)
                return false;
        } else if (!responsibleName.equals(other.responsibleName))
            return false;
        if (startHour == null) {
            if (other.startHour != null)
                return false;
        } else if (!startHour.equals(other.startHour))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttendanceParams [patientName=" + patientName + ", documentType=" + documentType + ", documentValue=" + documentValue
                + ", responsibleName=" + responsibleName + ", responsibleDocumentType=" + responsibleDocumentType
                + ", responsibleDocumentValue=" + responsibleDocumentValue + ", startHour=" + startHour + ", endHour=" + endHour + "]";
    }

}
