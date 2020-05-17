package br.com.tl.gdp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe NewPasswordRequestDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 27 de jan de 2020
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("New Password Request")
public class NewPasswordRequestDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -173297306237874136L;

    @NotNull(message = "Informe a senha antiga")
    private String oldPassword;

    @NotNull(message = "Informe a nova senha")
    private String newPassword;

    @NotNull(message = "Informe novamente a nova senha")
    private String confirmNewPassword;
}
