package br.com.ft.gdp.controller.filters;

import br.com.ft.gdp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponsibleFilters extends CustomFilters {

    private static final long serialVersionUID = -8122726857291316469L;

    private String professionalIdentity = "";

    private String cpf;

    private String firstName = "";

    private String lastName = "";

    public String getFirstName() {
        return "%".concat(firstName).concat("%");
    }

    public String getLastName() {
        return "%".concat(lastName).concat("%");
    }

}
