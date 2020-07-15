package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("Informações detalhadas do usuário")
public class UserInfoDTO extends PersonInfoDTO {

    private static final long serialVersionUID = 2270108536170182840L;

    @ApiModelProperty("Nome de usuário")
    @NotNull(message = "Informe o nome de usuário")
    private String userName;

    @ApiModelProperty("Data de criação do usuário")
    private LocalDateTime createdAt;

    @ApiModelProperty("Papéis do usuário")
    private List<RoleDTO> roles = new ArrayList<>();

}
