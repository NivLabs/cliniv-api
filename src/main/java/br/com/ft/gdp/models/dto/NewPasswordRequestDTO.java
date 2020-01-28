package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Classe NewPasswordRequestDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 27 de jan de 2020
 */

@Data
public class NewPasswordRequestDTO implements Serializable {

    private static final long serialVersionUID = -173297306237874136L;

    @NotNull(message = "Informe a senha antiga")
    private String oldPassword;

    @NotNull(message = "Informe a nova senha")
    private String newPassword;

    @NotNull(message = "Informe novamente a nova senha")
    private String confirmNewPassword;
}
