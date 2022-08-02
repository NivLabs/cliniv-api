package br.com.nivlabs.cliniv.models.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.nivlabs.cliniv.enums.DocumentType;

/**
 * Chave composta da tabela de documentos de uma pessoa
 * 
 * @author viniciosarodrigues
 *
 */
@Embeddable
public class PersonDocumentPK implements Serializable {

    private static final long serialVersionUID = 3200283738319249519L;

    @Column(name = "ID_PESSOA", insertable = false, updatable = false)
    private Long personId;

    @Column(name = "TIPO_DOCUMENTO")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Column(name = "VALOR")
    private String value;

    public PersonDocumentPK() {
        super();
    }

    public PersonDocumentPK(Long personId, DocumentType type, String value) {
        super();
        this.personId = personId;
        this.type = type;
        this.value = value;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((personId == null) ? 0 : personId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonDocumentPK other = (PersonDocumentPK) obj;
        if (personId == null) {
            if (other.personId != null)
                return false;
        } else if (!personId.equals(other.personId))
            return false;
        if (type != other.type)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PersonDocumentPK [personId=" + personId + ", type=" + type + ", value=" + value + "]";
    }

}
