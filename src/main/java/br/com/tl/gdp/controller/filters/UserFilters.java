package br.com.tl.gdp.controller.filters;

import br.com.tl.gdp.models.enums.Gender;
import br.com.tl.gdp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Filtros para pesquisa de Usuários
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Setter
public class UserFilters extends CustomFilters {

    private static final long serialVersionUID = 7760736861571277630L;

    private String userName = "";

    private String cpf = "";

    private String firstName = "";

    private String lastName = "";

    private Gender gender;

    public String getUserName() {
        return "%".concat(userName).concat("%");
    }

    public String getCpf() {
        return cpf;
    }

    public String getFirstName() {
        return "%".concat(firstName).concat("%");
    }

    public String getLastName() {
        return "%".concat(lastName).concat("%");
    }

    public Gender getGender() {
        return gender;
    }
}
