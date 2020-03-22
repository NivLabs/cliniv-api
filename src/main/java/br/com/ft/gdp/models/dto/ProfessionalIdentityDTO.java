package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Registro profissional do responsável de saúde (CRM, CRO, CRP, COREN, etc...)
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProfessionalIdentityDTO implements Serializable {

	private static final long serialVersionUID = -5712585514927819479L;

	private String registerType;

	private String registerValue;
}
