package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Representa a situação atual da API
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "API Status")
public class ServerStatusDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -8293328535500084535L;

    @ApiModelProperty("Data / Hora atual do servidor")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime currentDate = LocalDateTime.now();

    @ApiModelProperty("Nonme da aplicação")
    private String applicationName = "API Gestão de Prontuário";

    @ApiModelProperty("Veersão da aplicação")
    private String version = "DEV";

    @ApiModelProperty("Status do servidor")
    private String status = "Ligado";

    public LocalDateTime getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDateTime currentDate) {
        this.currentDate = currentDate;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((applicationName == null) ? 0 : applicationName.hashCode());
        result = prime * result + ((currentDate == null) ? 0 : currentDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        ServerStatusDTO other = (ServerStatusDTO) obj;
        if (applicationName == null) {
            if (other.applicationName != null)
                return false;
        } else if (!applicationName.equals(other.applicationName))
            return false;
        if (currentDate == null) {
            if (other.currentDate != null)
                return false;
        } else if (!currentDate.equals(other.currentDate))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServerStatusDTO [currentDate=" + currentDate + ", applicationName=" + applicationName + ", version="
                + version + ", status=" + status + "]";
    }

}
