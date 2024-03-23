package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.SupplierType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "FORNECEDOR")
public class Supplier extends BaseObjectWithId<String> {

    @Id
    private String id;

    @Column(name = "CPF_CNPJ")
    private String document;

    @Column(name = "NOME_FANTASIA")
    private String fantasyName;

    @Column(name = "RAZAO_SOCIAL")
    private String corporateReason;

    @Column(name = "INSCRICAO_ESTADUAL")
    private String stateRegistration;

    @Column(name = "INSCRICAO_MUNICIPAL")
    private String municipalResgistration;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO")
    private SupplierType type;

    @Column(name = "OBSERVACAO")
    private String observation;

    @Column(name = "TELEFONE_PRINCIPAL")
    private String principalPhone;

    @Column(name = "TELEFONE_SECUNDARIO")
    private String secondaryPhone;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private SupplierAddress address;

    public Supplier() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCorporateReason() {
        return corporateReason;
    }

    public void setCorporateReason(String corporateReason) {
        this.corporateReason = corporateReason;
    }

    public String getStateRegistration() {
        return stateRegistration;
    }

    public void setStateRegistration(String stateRegistration) {
        this.stateRegistration = stateRegistration;
    }

    public String getMunicipalResgistration() {
        return municipalResgistration;
    }

    public void setMunicipalResgistration(String municipalResgistration) {
        this.municipalResgistration = municipalResgistration;
    }

    public SupplierType getType() {
        return type;
    }

    public void setType(SupplierType type) {
        this.type = type;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SupplierAddress getAddress() {
        return address;
    }

    public void setAddress(SupplierAddress address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id) && Objects.equals(document, supplier.document) && Objects.equals(fantasyName, supplier.fantasyName) && Objects.equals(corporateReason, supplier.corporateReason) && Objects.equals(stateRegistration, supplier.stateRegistration) && Objects.equals(municipalResgistration, supplier.municipalResgistration) && type == supplier.type && Objects.equals(observation, supplier.observation) && Objects.equals(principalPhone, supplier.principalPhone) && Objects.equals(secondaryPhone, supplier.secondaryPhone) && Objects.equals(email, supplier.email) && Objects.equals(address, supplier.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, document, fantasyName, corporateReason, stateRegistration, municipalResgistration, type, observation, principalPhone, secondaryPhone, email, address);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", fantasyName='" + fantasyName + '\'' +
                ", corporateReason='" + corporateReason + '\'' +
                ", stateRegistration='" + stateRegistration + '\'' +
                ", municipalResgistration='" + municipalResgistration + '\'' +
                ", type=" + type +
                ", observation='" + observation + '\'' +
                ", principalPhone='" + principalPhone + '\'' +
                ", secondaryPhone='" + secondaryPhone + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
