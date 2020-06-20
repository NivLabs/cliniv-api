package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AllergyFilters extends CustomFilters {

    private static final long serialVersionUID = 1594221908802106263L;

    private String description = "";

    public String getDescription() {
        return "%".concat(this.description).concat("%");
    }

}
