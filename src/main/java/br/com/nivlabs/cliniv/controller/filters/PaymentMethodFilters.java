package br.com.nivlabs.cliniv.controller.filters;

import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

public class PaymentMethodFilters extends CustomFilters {

    private static final long serialVersionUID = -1802026370115693015L;

    private String id;

    private String name;

    public String getName() {
        return "%".concat(this.name != null ? this.name : "").concat("%");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SectorFilters [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
