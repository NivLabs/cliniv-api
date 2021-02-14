package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe InstituteInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@ApiModel(description = "Informações detalhadas da instituição")
public class InstituteInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -3104969606567859458L;

    @ApiModelProperty("CPNJ da instituição")
    private String cnpj;

    @ApiModelProperty("CNES")
    private String cnes;

    @ApiModelProperty("Nome fantasia")
    private String name;

    @ApiModelProperty("Nome empresarial")
    private String corporativeName;

    @ApiModelProperty("Natureza Legal")
    private String legalNature;

    @ApiModelProperty("Rua do endereço")
    private String street;

    @ApiModelProperty("Número do endereço")
    private String addressNumber;

    @ApiModelProperty("Complemento do endereço")
    private String complement;

    @ApiModelProperty("Código postal")
    private String postalCode;

    @ApiModelProperty("Estado do endereço")
    private String state;

    @ApiModelProperty("Bairro")
    private String neighborhood;

    @ApiModelProperty("Cidade")
    private String city;

    @ApiModelProperty("Telefone")
    private String phone;

    @ApiModelProperty("Dependência")
    private String dependency;

    @ApiModelProperty("Tipo da instituição")
    private String instituteType;

    @ApiModelProperty("Gestor")
    private String management;

    public InstituteInfoDTO() {
        super();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorporativeName() {
        return corporativeName;
    }

    public void setCorporativeName(String corporativeName) {
        this.corporativeName = corporativeName;
    }

    public String getLegalNature() {
        return legalNature;
    }

    public void setLegalNature(String legalNature) {
        this.legalNature = legalNature;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getInstituteType() {
        return instituteType;
    }

    public void setInstituteType(String instituteType) {
        this.instituteType = instituteType;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((cnes == null) ? 0 : cnes.hashCode());
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        result = prime * result + ((complement == null) ? 0 : complement.hashCode());
        result = prime * result + ((corporativeName == null) ? 0 : corporativeName.hashCode());
        result = prime * result + ((dependency == null) ? 0 : dependency.hashCode());
        result = prime * result + ((instituteType == null) ? 0 : instituteType.hashCode());
        result = prime * result + ((legalNature == null) ? 0 : legalNature.hashCode());
        result = prime * result + ((management == null) ? 0 : management.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
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
        InstituteInfoDTO other = (InstituteInfoDTO) obj;
        if (addressNumber == null) {
            if (other.addressNumber != null)
                return false;
        } else if (!addressNumber.equals(other.addressNumber))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (cnes == null) {
            if (other.cnes != null)
                return false;
        } else if (!cnes.equals(other.cnes))
            return false;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        if (complement == null) {
            if (other.complement != null)
                return false;
        } else if (!complement.equals(other.complement))
            return false;
        if (corporativeName == null) {
            if (other.corporativeName != null)
                return false;
        } else if (!corporativeName.equals(other.corporativeName))
            return false;
        if (dependency == null) {
            if (other.dependency != null)
                return false;
        } else if (!dependency.equals(other.dependency))
            return false;
        if (instituteType == null) {
            if (other.instituteType != null)
                return false;
        } else if (!instituteType.equals(other.instituteType))
            return false;
        if (legalNature == null) {
            if (other.legalNature != null)
                return false;
        } else if (!legalNature.equals(other.legalNature))
            return false;
        if (management == null) {
            if (other.management != null)
                return false;
        } else if (!management.equals(other.management))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (neighborhood == null) {
            if (other.neighborhood != null)
                return false;
        } else if (!neighborhood.equals(other.neighborhood))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "InstituteInfoDTO [cnpj=" + cnpj + ", cnes=" + cnes + ", name=" + name + ", corporativeName=" + corporativeName
                + ", legalNature=" + legalNature + ", street=" + street + ", addressNumber=" + addressNumber + ", complement=" + complement
                + ", postalCode=" + postalCode + ", state=" + state + ", neighborhood=" + neighborhood + ", city=" + city + ", phone="
                + phone + ", dependency=" + dependency + ", instituteType=" + instituteType + ", management=" + management + "]";
    }

}
