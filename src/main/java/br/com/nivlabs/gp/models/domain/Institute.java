package br.com.nivlabs.gp.models.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        return "Institute [id=" + id + ", cnpj=" + cnpj + ", cnes=" + cnes + ", companyLogo=" + companyLogo + ", name=" + name
                + ", corporativeName=" + corporativeName + ", legalNature=" + legalNature + ", street=" + street + ", addressNumber="
                + addressNumber + ", complement=" + complement + ", postalCode=" + postalCode + ", state=" + state + ", neighborhood="
                + neighborhood + ", city=" + city + ", phone=" + phone + ", dependency=" + dependency + ", instituteType=" + instituteType
                + ", managerName=" + managerName + ", managerPhone=" + managerPhone + ", managerMail=" + managerMail + ", licenseDate="
                + licenseDate + ", userOfRegister=" + userOfRegister + ", key=" + key + ", startDate=" + startDate + ", endDate=" + endDate
                + "]";
    }
}
