package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;

public class SectorFilters extends CustomFilters {

    private static final long serialVersionUID = 8928341349269829266L;

    private String id;

    private String description;

    public String getDescription() {
        return "%".concat(this.description != null ? this.description : "").concat("%");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        SectorFilters other = (SectorFilters) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SectorFilters [id=" + id + ", description=" + description + "]";
    }

}
