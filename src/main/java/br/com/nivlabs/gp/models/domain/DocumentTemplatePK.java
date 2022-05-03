package br.com.nivlabs.gp.models.domain;

import java.io.Serializable;
import java.util.Objects;

public class DocumentTemplatePK implements Serializable {

    private static final long serialVersionUID = -7840554737274545252L;

    private Long id;

    private Long userId;

    public DocumentTemplatePK() {
        super();
    }

    public DocumentTemplatePK(Long id, Long userId) {
        super();
        this.id = id;
        this.userId = userId;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DocumentTemplatePK other = (DocumentTemplatePK) obj;
        return Objects.equals(id, other.id) && Objects.equals(userId, other.userId);
    }

}
