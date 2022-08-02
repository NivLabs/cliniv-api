package br.com.nivlabs.cliniv.controller.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

public class DocumentTemplateFilter extends CustomFilters {

    private static final long serialVersionUID = 9133275768212486435L;

    @JsonIgnore
    private Long userId;

    private String description = "";

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return "%".concat(this.description).concat("%");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DocumentTemplateFilter [userId=");
        builder.append(userId);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

}
