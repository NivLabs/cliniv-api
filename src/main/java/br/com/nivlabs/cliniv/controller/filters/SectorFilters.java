package br.com.nivlabs.cliniv.controller.filters;

import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SectorFilters [id=");
        builder.append(id);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

}
