package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
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
@ApiModel("Credentials")
public class CredentialsDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -7495190733306523606L;

    private String username;

    private String email;

    @NotBlank
    @NotNull(message = "A senha deve ser informada")
    private String password;

}
