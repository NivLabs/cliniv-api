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

    @ApiModelProperty("Nome do gestor da clínica")
    private String managerName;

    @ApiModelProperty("Telefone/Celular de contato do gestor")
    private String managerPhone;

    @ApiModelProperty("E-mail de contato do gesto")
    private String managerMail;

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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerMail() {
        return managerMail;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }

    @Override
    public String toString() {
        return "CustomerInfoDTO [id=" + id + ", cnpj=" + cnpj + ", cnes=" + cnes + ", name=" + name + ", phone=" + phone
                + ", corporativeName=" + corporativeName + ", legalNature=" + legalNature + ", dependency=" + dependency
                + ", instituteType=" + instituteType + ", managerName=" + managerName + ", managerPhone=" + managerPhone + ", managerMail="
                + managerMail + ", license=" + license + ", address=" + address + ", logoBase64=" + logoBase64 + "]";
    }

}
