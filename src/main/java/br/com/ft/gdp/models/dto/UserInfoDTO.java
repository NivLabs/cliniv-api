package br.com.ft.gdp.models.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Esta classe representa as informações detalhadas do usuário, serve para criação e atualização das informações do usuário
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("UserInformations")
public class UserInfoDTO extends PersonInfoDTO {

    private static final long serialVersionUID = 2270108536170182840L;

    @NotNull(message = "Informe o nome de usuário")
    private String userName;

    private String email;

    private List<RoleDTO> roles = new ArrayList<>();

}
