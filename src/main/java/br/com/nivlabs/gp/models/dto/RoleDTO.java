package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe interna para representar os papéis do usuário
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Papel de acesso (role)")
public class RoleDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -4723369199443894800L;

    @ApiModelProperty("Identificador único do papel do usuário")
    private Long id;

    @ApiModelProperty("Nome do papel do usuário")
    private String name;

    @ApiModelProperty("Descrição do papel do usuáiro")
    private String description;
}