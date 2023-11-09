package br.com.nivlabs.cliniv.models.domain;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.enums.SupplierType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;

@Entity
@Table(name = "FORNECEDOR")
public class Supplier extends BaseObjectWithId {

    private static final long serialVersionUID = -6757073229237534589L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DOCUMENTO")
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO")
    private SupplierType type;

    @Column(name = "NOME")
    private String name;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private SupplierAddress address;

    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private SupplierPaymentInformation paymentInformation;

    public Supplier() {
        super();
    }

    public Supplier(Long id, String document, SupplierType type, String name, SupplierAddress address,
            SupplierPaymentInformation paymentInformation) {
        super();
        this.id = id;
        this.document = document;
        this.type = type;
        this.name = name;
        this.address = address;
        this.paymentInformation = paymentInformation;
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

    public SupplierType getType() {
        return type;
    }

    public void setType(SupplierType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierAddress getAddress() {
        return address;
    }

    public void setAddress(SupplierAddress address) {
        this.address = address;
    }

    public SupplierPaymentInformation getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(SupplierPaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, id, name, type);
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
        return Objects.equals(document, other.document) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && type == other.type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Supplier [id=");
        builder.append(id);
        builder.append(", document=");
        builder.append(document);
        builder.append(", type=");
        builder.append(type);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
