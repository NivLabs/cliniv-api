package br.com.nivlabs.gp.models.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
 * Classe Institute.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Entity
@Table(name = "INSTITUTO")
public class Institute implements Serializable {

    private static final long serialVersionUID = -8389926388826078313L;

    @Id
    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "CNES")
    private String cnes;

    @Lob
    @Column(name = "LOGO")
    private String companyLogo;

    @Column(name = "NOME")
    private String name;

    @Column(name = "NOME_CORPORATIVO")
    private String corporativeName;

    @Column(name = "NATUREZA_LEGAL")
    private String legalNature;

    @Column(name = "LOGRADOURO")
    private String street;

    @Column(name = "NUMERO")
    private String addressNumber;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "CEP")
    private String postalCode;

    @Column(name = "ESTADO")
    private String state;

    @Column(name = "BAIRRO")
    private String neighborhood;

    @Column(name = "CIDADE")
    private String city;

    @Column(name = "TELEFONE")
    private String phone;

    @Column(name = "DEPENDENCIA")
    private String dependency;

    @Column(name = "TIPO_DE_INSTITUICAO")
    private String instituteType;

    @Column(name = "GESTAO")
    private String management;

    @Column(name = "DATA_DE_REGISTRO")
    private Date licenseDate;

    @Column(name = "USUARIO_DE_REGISTRO")
    private String userOfRegister;

    @Column(name = "CHAVE_ACESSO")
    private String key;

    @Column(name = "DATA_INICIO")
    private LocalDateTime startDate;

    @Column(name = "DATA_FIM")
    private LocalDateTime endDate;

    public Institute() {
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

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
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

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getUserOfRegister() {
        return userOfRegister;
    }

    public void setUserOfRegister(String userOfRegister) {
        this.userOfRegister = userOfRegister;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((cnes == null) ? 0 : cnes.hashCode());
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        result = prime * result + ((companyLogo == null) ? 0 : companyLogo.hashCode());
        result = prime * result + ((complement == null) ? 0 : complement.hashCode());
        result = prime * result + ((corporativeName == null) ? 0 : corporativeName.hashCode());
        result = prime * result + ((dependency == null) ? 0 : dependency.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((instituteType == null) ? 0 : instituteType.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((legalNature == null) ? 0 : legalNature.hashCode());
        result = prime * result + ((licenseDate == null) ? 0 : licenseDate.hashCode());
        result = prime * result + ((management == null) ? 0 : management.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((userOfRegister == null) ? 0 : userOfRegister.hashCode());
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
        Institute other = (Institute) obj;
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
        if (companyLogo == null) {
            if (other.companyLogo != null)
                return false;
        } else if (!companyLogo.equals(other.companyLogo))
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
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (instituteType == null) {
            if (other.instituteType != null)
                return false;
        } else if (!instituteType.equals(other.instituteType))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (legalNature == null) {
            if (other.legalNature != null)
                return false;
        } else if (!legalNature.equals(other.legalNature))
            return false;
        if (licenseDate == null) {
            if (other.licenseDate != null)
                return false;
        } else if (!licenseDate.equals(other.licenseDate))
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
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
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
        if (userOfRegister == null) {
            if (other.userOfRegister != null)
                return false;
        } else if (!userOfRegister.equals(other.userOfRegister))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Institute [cnpj=" + cnpj + ", cnes=" + cnes + ", companyLogo=" + companyLogo + ", name=" + name + ", corporativeName="
                + corporativeName + ", legalNature=" + legalNature + ", street=" + street + ", addressNumber=" + addressNumber
                + ", complement=" + complement + ", postalCode=" + postalCode + ", state=" + state + ", neighborhood=" + neighborhood
                + ", city=" + city + ", phone=" + phone + ", dependency=" + dependency + ", instituteType=" + instituteType
                + ", management=" + management + ", licenseDate=" + licenseDate + ", userOfRegister=" + userOfRegister + ", key=" + key
                + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

}
