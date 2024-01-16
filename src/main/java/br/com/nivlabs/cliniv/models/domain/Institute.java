package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.manager.models.CGCType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

/**
 * Classe Institute.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 30 de nov de 2019
 */
@Entity
@Table(name = "INSTITUTO")
public class Institute extends BaseObjectWithId {

    private static final long serialVersionUID = -8389926388826078313L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CGC_TIPO")
    @Enumerated(EnumType.STRING)
    private CGCType cgcType;

    @Column(name = "CGC")
    private String cgc;

    @Column(name = "CNES")
    private String cnes;

    @Lob
    @Column(name = "LOGO")
    private byte[] companyLogo;

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

    @Column(name = "GESTOR")
    private String managerName;

    @Column(name = "TELEFONE_GESTOR")
    private String managerPhone;

    @Column(name = "EMAIL_GESTOR")
    private String managerMail;

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

    public String getCompanyLogo() {
        return companyLogo != null ? new String(companyLogo, StandardCharsets.UTF_8) : null;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo != null ? companyLogo.getBytes() : null;
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Institute [id=");
        builder.append(id);
        builder.append(", cgcType=");
        builder.append(cgcType);
        builder.append(", cgc=");
        builder.append(cgc);
        builder.append(", cnes=");
        builder.append(cnes);
        builder.append(", companyLogo=");
        builder.append(companyLogo);
        builder.append(", name=");
        builder.append(name);
        builder.append(", corporativeName=");
        builder.append(corporativeName);
        builder.append(", legalNature=");
        builder.append(legalNature);
        builder.append(", street=");
        builder.append(street);
        builder.append(", addressNumber=");
        builder.append(addressNumber);
        builder.append(", complement=");
        builder.append(complement);
        builder.append(", postalCode=");
        builder.append(postalCode);
        builder.append(", state=");
        builder.append(state);
        builder.append(", neighborhood=");
        builder.append(neighborhood);
        builder.append(", city=");
        builder.append(city);
        builder.append(", phone=");
        builder.append(phone);
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
        builder.append(", licenseDate=");
        builder.append(licenseDate);
        builder.append(", userOfRegister=");
        builder.append(userOfRegister);
        builder.append(", key=");
        builder.append(key);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append("]");
        return builder.toString();
    }

}
