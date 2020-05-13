package br.com.ft.gdp.controller.filters;

import br.com.ft.gdp.models.domain.PatientType;
import br.com.ft.gdp.models.enums.ActiveType;
import br.com.ft.gdp.models.enums.EntryType;
import br.com.ft.gdp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Filtros para busca paginada de Atendimentos
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceFilters extends CustomFilters {

    private static final long serialVersionUID = 4716373803667305120L;
    private String sectorId;
    private String firstName = "";
    private String lastName = "";
    private String cpf;
    private ActiveType activeType;
    private PatientType patientType;
    private EntryType entryType;

    public ActiveType getActiveType() {
        if (activeType == null)
            this.activeType = ActiveType.ACTIVE;
        return this.activeType;
    }

    public String getFirstName() {
        return "%".concat(firstName).concat("%");
    }

    public String getLastName() {
        return "%".concat(lastName).concat("%");
    }
}
