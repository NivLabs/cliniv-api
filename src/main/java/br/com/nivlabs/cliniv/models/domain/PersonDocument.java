package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObject;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author viniciosarodrigues
 */
@Entity
@Table(name = "DOCUMENTO_PESSOA")
public class PersonDocument extends BaseObject {

    @EmbeddedId
    private PersonDocumentPK id;

    @Column(name = "EXPEDIDOR")
    private String dispatcher;

    @Column(name = "DATA_EXPEDICAO")
    private LocalDate expeditionDate;

    @Column(name = "VALIDADE")
    private LocalDate validate;

    @Column(name = "UF_EXPEDICAO")
    private String uf;

    @MapsId("personId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

    public PersonDocument() {
        super();
    }

    public PersonDocumentPK getId() {
        return id;
    }

    public void setId(PersonDocumentPK id) {
        this.id = id;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public LocalDate getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(LocalDate expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDocument that = (PersonDocument) o;
        return Objects.equals(id, that.id) && Objects.equals(dispatcher, that.dispatcher) && Objects.equals(expeditionDate, that.expeditionDate) && Objects.equals(validate, that.validate) && Objects.equals(uf, that.uf) && Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dispatcher, expeditionDate, validate, uf, person);
    }

    @Override
    public String toString() {
        return "PersonDocument{" +
                "id=" + id +
                ", dispatcher='" + dispatcher + '\'' +
                ", expeditionDate=" + expeditionDate +
                ", validate=" + validate +
                ", uf='" + uf + '\'' +
                ", person=" + person +
                '}';
    }

}
