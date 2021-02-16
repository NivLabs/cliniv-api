package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;

/**
 * Filtro de pesquisa dinâmica para formulários de anamnese
 * 
 * @author viniciosarodrigues
 *
 */
public class AnamnesisFormFilters extends CustomFilters {

    private static final long serialVersionUID = -3760484564774275094L;

    private String id;

    private String title = "";

    public String getTitle() {
        return "%".concat(title).concat("%");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AnamnesisFormFilters [id=" + id + ", title=" + title + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnamnesisFormFilters other = (AnamnesisFormFilters) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}
