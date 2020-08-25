package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Informações do cliente")
public class CustomerInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6019817310193995713L;

    @ApiModelProperty("Identificador único do Cliente")
    private Long id;

    @ApiModelProperty("CNPJ")
    private String cnpj;

    @ApiModelProperty("Cadastro Nacional de Estabelecimento de saúde")
    private String cnes;

    @ApiModelProperty("Nome da instituição")
    private String name;

    @ApiModelProperty("Telefone")
    private String phone;

    @ApiModelProperty("Nome corporativo")
    private String corporativeName;

    @ApiModelProperty("Natureza Legal")
    private String legalNature;

    @ApiModelProperty("Dependência")
    private String dependency;

    @ApiModelProperty("Tipo da instituição")
    private String instituteType;

    @ApiModelProperty("Gestor responsável")
    private String management;

    @ApiModelProperty("Licença de uso")
    private LicenseDTO license;

    @ApiModelProperty("Endereço da instituição")
    private AddressDTO address;

    @ApiModelProperty("Logotipo da instituição")
    private String logoBase64;

    public CustomerInfoDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public LicenseDTO getLicense() {
        return license;
    }

    public void setLicense(LicenseDTO license) {
        this.license = license;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((cnes == null) ? 0 : cnes.hashCode());
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        result = prime * result + ((corporativeName == null) ? 0 : corporativeName.hashCode());
        result = prime * result + ((dependency == null) ? 0 : dependency.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((instituteType == null) ? 0 : instituteType.hashCode());
        result = prime * result + ((legalNature == null) ? 0 : legalNature.hashCode());
        result = prime * result + ((license == null) ? 0 : license.hashCode());
        result = prime * result + ((logoBase64 == null) ? 0 : logoBase64.hashCode());
        result = prime * result + ((management == null) ? 0 : management.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
        CustomerInfoDTO other = (CustomerInfoDTO) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
        if (license == null) {
            if (other.license != null)
                return false;
        } else if (!license.equals(other.license))
            return false;
        if (logoBase64 == null) {
            if (other.logoBase64 != null)
                return false;
        } else if (!logoBase64.equals(other.logoBase64))
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
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerInfoDTO [id=" + id + ", cnpj=" + cnpj + ", cnes=" + cnes + ", name=" + name + ", phone=" + phone
                + ", corporativeName=" + corporativeName + ", legalNature=" + legalNature + ", dependency=" + dependency
                + ", instituteType=" + instituteType + ", management=" + management + ", license=" + license + ", address=" + address
                + ", logoBase64=" + logoBase64 + "]";
    }

}
