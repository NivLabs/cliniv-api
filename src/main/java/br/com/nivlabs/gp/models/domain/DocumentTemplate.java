package br.com.nivlabs.gp.models.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "TEMPLATE_DOC")
@IdClass(DocumentTemplatePK.class)
public class DocumentTemplate extends BaseObjectWithId {

    private static final long serialVersionUID = -6658552159562710059L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "ID_USUARIO")
    private Long userId;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "TEXTO_TEMPLATE")
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, text, userId);
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
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(text, other.text)
                && Objects.equals(userId, other.userId);
    }

}
