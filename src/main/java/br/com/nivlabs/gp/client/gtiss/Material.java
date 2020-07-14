package br.com.nivlabs.gp.client.gtiss;

import br.com.nivlabs.gp.client.RestClientObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Objeto de Materiais da ANS
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Material extends RestClientObject {
	
	private static final long serialVersionUID = 1885369994400553200L;
	
	private String id;
	private String code;
	private String description;
	private String model;
	private String anvisaRegistration;
	private String riskClass;
	private String technicalDescription;
	private String manufacturer;
	private String expirationStartDate;
	private String expirationDate;
	private String implantationEndDate;
}
