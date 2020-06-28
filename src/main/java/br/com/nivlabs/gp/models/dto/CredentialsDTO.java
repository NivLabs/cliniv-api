package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe CredentialsDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Credenciais - Utilizar e-mail ou senha para realizar o login")
public class CredentialsDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -7495190733306523606L;

    @ApiModelProperty("Nome de usuário")
    private String username;

    @ApiModelProperty("Email")
    private String email;

    @ApiModelProperty("Senha")
    @NotBlank
    @NotNull(message = "A senha deve ser informada")
    private String password;

}
