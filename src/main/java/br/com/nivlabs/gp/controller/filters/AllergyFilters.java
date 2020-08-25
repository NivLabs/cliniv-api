package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;

public class AllergyFilters extends CustomFilters {

    private static final long serialVersionUID = 1594221908802106263L;

    private String description = "";

    public String getDescription() {
        return "%".concat(this.description).concat("%");
    }

    public AllergyFilters() {
        super();
    }

    public AllergyFilters(Integer page, Integer size, String orderBy, String direction) {
        super(page, size, orderBy, direction);
    }

    public void setDescription(String description) {
        this.description = description;
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
        AllergyFilters other = (AllergyFilters) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AllergyFilters [description=" + description + "]";
    }

}
