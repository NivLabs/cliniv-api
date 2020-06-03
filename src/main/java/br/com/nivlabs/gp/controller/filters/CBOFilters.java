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
public class CBOFilters extends CustomFilters {

	private static final long serialVersionUID = 3609981747639711452L;

	private String id;

	private String description;

	public String getDescription() {
		return "%".concat(description != null ? description : "").concat("%");
	}
}
