package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.enums.Modality;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Filtro customizado para busca paginada de operadoras de saúde
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class HealthOperatorFilters extends CustomFilters {

    private static final long serialVersionUID = -7472397996695208164L;

    private String id;

    private String ansCode;

    private String cnpj;

    private String companyName;

    private String fantasyName;

    private Modality modality;

    public String getCompanyName() {
        return "%".concat(this.companyName).concat("%");
    }

    public String getFantasyName() {
        return "%".concat(this.fantasyName).concat("%");
    }
}
