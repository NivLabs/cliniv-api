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
public class SectorFilters extends CustomFilters {

	private static final long serialVersionUID = 8928341349269829266L;

	private String id;

	private String description;

	public String getDescription() {
		return "%".concat(this.description).concat("%");
	}
}
