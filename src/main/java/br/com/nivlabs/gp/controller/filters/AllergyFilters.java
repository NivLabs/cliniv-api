package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;

public class AllergyFilters extends CustomFilters {

    private static final long serialVersionUID = 1594221908802106263L;

    private String description = "";

    public AllergyFilters() {
        super();
    }

    public AllergyFilters(Integer page, Integer size, String orderBy, String direction) {
        super(page, size, orderBy, direction);
    }

    public String getDescription() {
        return "%".concat(this.description).concat("%");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AllergyFilters [description=" + description + "]";
    }

}
