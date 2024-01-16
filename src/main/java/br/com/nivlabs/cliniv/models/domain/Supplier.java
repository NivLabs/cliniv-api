package br.com.nivlabs.cliniv.models.domain;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import br.com.nivlabs.cliniv.enums.SupplierType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;

@Entity
@Table(name = "FORNECEDOR")
public class Supplier extends BaseObjectWithId {

    private static final long serialVersionUID = -6757073229237534589L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public int hashCode() {
        return Objects.hash(address, corporateReason, document, email, fantasyName, id, municipalResgistration, observation, principalPhone,
                            secondaryPhone, stateRegistration, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Supplier other = (Supplier) obj;
        return Objects.equals(address, other.address) && Objects.equals(corporateReason, other.corporateReason)
                && Objects.equals(document, other.document) && Objects.equals(email, other.email)
                && Objects.equals(fantasyName, other.fantasyName) && Objects.equals(id, other.id)
                && Objects.equals(municipalResgistration, other.municipalResgistration) && Objects.equals(observation, other.observation)
                && Objects.equals(principalPhone, other.principalPhone) && Objects.equals(secondaryPhone, other.secondaryPhone)
                && Objects.equals(stateRegistration, other.stateRegistration) && type == other.type;
    }

}
