package br.com.ft.gdp.models.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe ResponsibleDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponsibleDTO extends PersonDTO {
	private static final long serialVersionUID = -5141572031863436326L;

	private String professionalIdentity;

	private List<SpecialityDTO> specializations = new ArrayList<SpecialityDTO>();
}
