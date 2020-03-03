package br.com.ft.gdp.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe ResponsibleInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponsibleInfoDTO extends PersonInfoDTO {

	private static final long serialVersionUID = 3558512431533807447L;

	private ProfessionalIdentityDTO professionalIdentity;

}
