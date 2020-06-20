package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel("Identidade Profissional")
public class ProfessionalIdentityDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5712585514927819479L;

    private String registerType;

    private String registerValue;
}
