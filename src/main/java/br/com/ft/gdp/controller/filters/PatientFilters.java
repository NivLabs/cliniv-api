package br.com.ft.gdp.controller.filters;

import br.com.ft.gdp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Filtro de pesquisa dinâmica para paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PatientFilters extends CustomFilters {

    private static final long serialVersionUID = 1647722811566982336L;

    private String id;

      private String cpf;

    private String firstName = "";

    private String lastName = "";
    
    private String susNumber = "";

    public String getFirstName() {
        return "%".concat(firstName).concat("%");
    }

    public String getLastName() {
        return "%".concat(lastName).concat("%");
    }
}
