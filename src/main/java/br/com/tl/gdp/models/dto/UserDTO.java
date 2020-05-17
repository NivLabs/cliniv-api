package br.com.tl.gdp.models.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Esta classe representa as informações reduzidas do usuário, mais utilizada para listagens e paginações
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("User")
public class UserDTO extends PersonDTO {

    private static final long serialVersionUID = 2375401831562989624L;

    private String userName;

}
