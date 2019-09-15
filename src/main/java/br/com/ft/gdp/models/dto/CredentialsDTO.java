package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Classe CredentialsDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Data
public class CredentialsDTO implements Serializable {

    private static final long serialVersionUID = -7495190733306523606L;

    private String username;

    private String email;

    @NotBlank
    @NotNull(message = "A senha deve ser informada")
    private String password;

}
