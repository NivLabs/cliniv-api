package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Classe NewPasswordRequestDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Data
public class NewPasswordRequestDTO implements Serializable {

    private static final long serialVersionUID = 8663867905476131957L;

    @NotNull(message = "Informe um e-mail ou nome de usuário! [usernameOrEmail]")
    private String usernameOrEmail;

    @NotNull(message = "Informe o nome materno completo! [motherName]")
    private String motherName;

    @NotNull(message = "Informe a data de nascimento! [bornDate]")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date bornDate;

    @NotNull(message = "Informe a nova senha! [newPassword]")
    private String newPassword;
}
