package br.com.nivlabs.cliniv.models.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações da instituição")
public class InstituteDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5796305672599055540L;

    private String appName = "NiV Labs - Gestão de Prontuário";
    private String version = "1.0.0-beta";
    private CustomerInfoDTO customerInfo;
    private Set<ParameterDTO> parameters = new HashSet<>();

    public InstituteDTO() {
        super();
    }

    public InstituteDTO(String appName, String version, CustomerInfoDTO customerInfo, Set<ParameterDTO> parameters) {
        super();
        this.appName = appName;
        this.version = version;
        this.customerInfo = customerInfo;
        this.parameters = parameters;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CustomerInfoDTO getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoDTO customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Set<ParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterDTO> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appName == null) ? 0 : appName.hashCode());
        result = prime * result + ((customerInfo == null) ? 0 : customerInfo.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
        InstituteDTO other = (InstituteDTO) obj;
        if (appName == null) {
            if (other.appName != null)
                return false;
        } else if (!appName.equals(other.appName))
            return false;
        if (customerInfo == null) {
            if (other.customerInfo != null)
                return false;
        } else if (!customerInfo.equals(other.customerInfo))
            return false;
        if (parameters == null) {
            if (other.parameters != null)
                return false;
        } else if (!parameters.equals(other.parameters))
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
        return "InstituteDTO [appName=" + appName + ", version=" + version + ", customerInfo=" + customerInfo + ", parameters=" + parameters
                + "]";
    }

}
