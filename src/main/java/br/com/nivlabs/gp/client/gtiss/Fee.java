package br.com.nivlabs.gp.client.gtiss;

import br.com.nivlabs.gp.client.RestClientObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Objeto de Taxas da ANS
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
public class Fee extends RestClientObject {

	private static final long serialVersionUID = 139714345053962715L;
	
	private String id;
	private String code;
	private String description;
	private String detail;
	private String expirationStartDate;
	private String expirationDate;
	private String implantationEndDate;
}