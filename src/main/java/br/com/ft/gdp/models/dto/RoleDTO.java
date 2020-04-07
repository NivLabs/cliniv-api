package br.com.ft.gdp.models.dto;

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
public class RoleDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -4723369199443894800L;

    private Long id;

    private String description;
}