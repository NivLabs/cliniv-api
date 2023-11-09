package br.com.nivlabs.cliniv.models.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;

@Entity
@Table(name = "ENDERECO_FORNECEDOR")
public class SupplierAddress extends BaseObjectWithId {

    private static final long serialVersionUID = 7701185672879682675L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FORNECEDOR")
    private Supplier supplier;

    @Column(name = "LOGRADOURO")
    private String street;

    @Column(name = "NUMERO")
    private String addressNumber;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "CODIGO_POSTAL")
    private String postalCode;

    @Column(name = "ESTADO")
    private String state;

    @Column(name = "BAIRRO")
    private String neighborhood;

    @Column(name = "CIDADE")
    private String city;

    public SupplierAddress() {
        super();
    }

    public SupplierAddress(Long id, Supplier supplier, String street, String addressNumber, String complement, String postalCode,
            String state, String neighborhood, String city) {
        super();
        this.id = id;
        this.supplier = supplier;
        this.street = street;
        this.addressNumber = addressNumber;
        this.complement = complement;
        this.postalCode = postalCode;
        this.state = state;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    @Override
    public int hashCode() {
        return Objects.hash(addressNumber, city, complement, id, neighborhood, postalCode, state, street, supplier);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SupplierAddress other = (SupplierAddress) obj;
        return Objects.equals(addressNumber, other.addressNumber) && Objects.equals(city, other.city)
                && Objects.equals(complement, other.complement) && Objects.equals(id, other.id)
                && Objects.equals(neighborhood, other.neighborhood) && Objects.equals(postalCode, other.postalCode)
                && Objects.equals(state, other.state) && Objects.equals(street, other.street) && Objects.equals(supplier, other.supplier);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SupplierAddress [id=");
        builder.append(id);
        builder.append(", supplier=");
        builder.append(supplier);
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
        builder.append("]");
        return builder.toString();
    }

}
