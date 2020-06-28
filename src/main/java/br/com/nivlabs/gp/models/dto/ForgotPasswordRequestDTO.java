package br.com.nivlabs.gp.models.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe NewPasswordRequestDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Requisição de Senha perdida")
public class ForgotPasswordRequestDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8663867905476131957L;

    @ApiModelProperty("Nome de usuário ou e-mail")
    @NotNull(message = "Informe um e-mail ou nome de usuário!")
    private String usernameOrEmail;

    @ApiModelProperty("Nome completo da mãe")
    @NotNull(message = "Informe o nome materno completo!")
    private String motherName;

    @ApiModelProperty("Data de nascimento")
    @NotNull(message = "Informe a data de nascimento!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date bornDate;

    @ApiModelProperty("Nova senha")
    @NotNull(message = "Informe a nova senha!")
    private String newPassword;
}
