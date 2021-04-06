package br.com.nivlabs.gp.models.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObject;

/**
 * @author viniciosarodrigues
 *
 */
@Entity
@Table(name = "DOCUMENTO_PESSOA")
public class PersonDocument extends BaseObject {

    private static final long serialVersionUID = 58027003453495426L;

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
    public String toString() {
        return "PersonDocument [id=" + id + ", dispatcher=" + dispatcher + ", expeditionDate=" + expeditionDate + ", validate=" + validate
                + ", uf=" + uf + ", person=" + person + "]";
    }

}
