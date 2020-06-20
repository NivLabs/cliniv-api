package br.com.nivlabs.gp.models.dto;

import br.com.nivlabs.gp.models.enums.MetaType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Parâmetros da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Parâmetro da apliação")
public class ParameterDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 647659232405015211L;

    private Long id;

    private String name;

    private String group;

    private MetaType metaType;

    private String value;

    private String[] groupValues;

}
