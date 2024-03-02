package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Classe PersonAddress.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 18 de out de 2019
 */
@Entity
@Table(name = "ENDERECO_PF")
public class PersonAddress extends BaseObjectWithId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

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

    public PersonAddress() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonAddress that = (PersonAddress) o;
        return Objects.equals(id, that.id) && Objects.equals(person, that.person) && Objects.equals(street, that.street) && Objects.equals(addressNumber, that.addressNumber) && Objects.equals(complement, that.complement) && Objects.equals(postalCode, that.postalCode) && Objects.equals(state, that.state) && Objects.equals(neighborhood, that.neighborhood) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, street, addressNumber, complement, postalCode, state, neighborhood, city);
    }

    @Override
    public String toString() {
        return "PersonAddress{" +
                "id=" + id +
                ", person=" + person +
                ", street='" + street + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", complement='" + complement + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", state='" + state + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

}
