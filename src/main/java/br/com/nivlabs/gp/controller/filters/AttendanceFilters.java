package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.enums.ActiveType;
import br.com.nivlabs.gp.enums.EntryType;
import br.com.nivlabs.gp.enums.PatientType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
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
    private String fullName = "";
    private String socialName = "";
    private String cpf;
    private ActiveType activeType;
    private PatientType patientType;
    private EntryType entryType;

    public ActiveType getActiveType() {
        if (activeType == null)
            this.activeType = ActiveType.ACTIVE;
        return this.activeType;
    }

    public String getFullName() {
        return "%".concat(fullName).concat("%");
    }

    public String getSocialName() {
        return "%".concat(socialName).concat("%");
    }
}
