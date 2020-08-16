package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.repository.custom.CustomFilters;
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

    private String id;

    private String professionalIdentity = "";

    private String cpf;

    private String fullName = "";

    private String socialName = "";

    public String getFullName() {
        return "%".concat(fullName).concat("%");
    }

    public String getSocialName() {
        return "%".concat(socialName).concat("%");
    }

}
