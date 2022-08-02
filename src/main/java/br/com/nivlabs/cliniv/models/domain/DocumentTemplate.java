package br.com.nivlabs.cliniv.models.domain;

import java.util.Base64;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObject;

@Entity
@Table(name = "TEMPLATE_DOC")
public class DocumentTemplate extends BaseObject {

    private static final long serialVersionUID = -6658552159562710059L;

    @Id
    private DocumentTemplatePK pk;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "TEXTO_TEMPLATE")
    @Lob
    private String text;

    public DocumentTemplatePK getPk() {
        return pk;
    }

    public void setPk(DocumentTemplatePK pk) {
        this.pk = pk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text != null ? new String(Base64.getDecoder().decode(text)) : null;
    }

    public void setText(String text) {
        this.text = text != null ? Base64.getEncoder().encodeToString(text.getBytes()) : null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, pk, text);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DocumentTemplate other = (DocumentTemplate) obj;
        return Objects.equals(description, other.description) && Objects.equals(pk, other.pk) && Objects.equals(text, other.text);
    }
}
