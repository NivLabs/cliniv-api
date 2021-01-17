package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;

/**
 * Filtros para pesquisa de especialidades
 * 
 * @author viniciosarodrigues
 *
 */
public class SpecialityFilter extends CustomFilters {

    private static final long serialVersionUID = 1887665841868314477L;

    private String description = "";

    public String getDescription() {
        return "%".concat(description).concat("%");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SpecialityFilter [description=" + description + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
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
        SpecialityFilter other = (SpecialityFilter) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

}
