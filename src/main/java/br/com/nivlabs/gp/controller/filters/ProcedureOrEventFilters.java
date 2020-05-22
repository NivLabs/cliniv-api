package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.models.enums.ActiveType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcedureOrEventFilters extends CustomFilters {

	private static final long serialVersionUID = -8122726857291316469L;

	private String id;

	private String description = "";

	private ActiveType activeType;

	public String getDescription() {
		return "%".concat(this.description).concat("%");
	}
}
