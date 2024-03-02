package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Entity
@Table(name = "TEMPLATE_DOC")
public class DocumentTemplate extends BaseObjectWithId<DocumentTemplatePK> {
    
    @Id
    private DocumentTemplatePK id;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "TEXTO_TEMPLATE")
    @Lob
    private byte[] text;

    public DocumentTemplatePK getId() {
        return id;
    }

    public void setId(DocumentTemplatePK id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text != null ? new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8) : null;
    }

    public void setText(String text) {
        this.text = text != null ? Base64.getEncoder().encode(text.getBytes()) : null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id);
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
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(text, other.text);
    }
}
