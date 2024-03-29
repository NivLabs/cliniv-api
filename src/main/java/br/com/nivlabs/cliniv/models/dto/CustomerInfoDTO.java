package br.com.nivlabs.cliniv.models.dto;

import br.com.nivlabs.cliniv.manager.models.CGCType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações do cliente")
public class CustomerInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6019817310193995713L;

    @Schema(description = "Identificador único do Cliente")
    private Long id;

    @Schema(description = "Tipo de CGC")
    private CGCType cgcType;

    @Schema(description = "Cadastro geral do contribuinte (CPF/CNPJ)")
    private String cgc;

    @Schema(description = "Cadastro Nacional de Estabelecimento de saúde")
    private String cnes;

    @Schema(description = "Nome da instituição")
    private String name;

    @Schema(description = "Telefone")
    private String phone;

    @Schema(description = "Nome corporativo")
    private String corporativeName;

    @Schema(description = "Natureza Legal")
    private String legalNature;

    @Schema(description = "Dependência")
    private String dependency;

    @Schema(description = "Tipo da instituição")
    private String instituteType;

    @Schema(description = "Nome do gestor da clínica")
    private String managerName;

    @Schema(description = "Telefone/Celular de contato do gestor")
    private String managerPhone;

    @Schema(description = "E-mail de contato do gesto")
    private String managerMail;

    @Schema(description = "Licença de uso")
    private LicenseDTO license;

    @Schema(description = "Endereço da instituição")
    private AddressDTO address;

    @Schema(description = "Logotipo da instituição")
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

    public CGCType getCgcType() {
        return cgcType;
    }

    public void setCgcType(CGCType cgcType) {
        this.cgcType = cgcType;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
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
        StringBuilder builder = new StringBuilder();
        builder.append("CustomerInfoDTO [id=");
        builder.append(id);
        builder.append(", cgcType=");
        builder.append(cgcType);
        builder.append(", cgc=");
        builder.append(cgc);
        builder.append(", cnes=");
        builder.append(cnes);
        builder.append(", name=");
        builder.append(name);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", corporativeName=");
        builder.append(corporativeName);
        builder.append(", legalNature=");
        builder.append(legalNature);
        builder.append(", dependency=");
        builder.append(dependency);
        builder.append(", instituteType=");
        builder.append(instituteType);
        builder.append(", managerName=");
        builder.append(managerName);
        builder.append(", managerPhone=");
        builder.append(managerPhone);
        builder.append(", managerMail=");
        builder.append(managerMail);
        builder.append(", license=");
        builder.append(license);
        builder.append(", address=");
        builder.append(address);
        builder.append(", logoBase64=");
        builder.append(logoBase64);
        builder.append("]");
        return builder.toString();
    }

}
