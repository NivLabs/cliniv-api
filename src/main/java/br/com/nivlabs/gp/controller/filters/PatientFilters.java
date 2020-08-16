package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.enums.PatientType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
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

    private String fullName = "";

    private String socialName = "";

    private String susNumber = "";

    private PatientType type;

    public String getFullName() {
        return "%".concat(fullName).concat("%");
    }

    public String getSocialName() {
        return "%".concat(socialName).concat("%");
    }
}
