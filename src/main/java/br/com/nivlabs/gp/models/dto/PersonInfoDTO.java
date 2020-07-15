package br.com.nivlabs.gp.models.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.GenderIdeology;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PersonInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações detalhadas da pessoa")
public abstract class PersonInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4735834196671409605L;

    @ApiModelProperty("Identificadfor único")
    private Long id;

    @ApiModelProperty("Primeiro nome da pessoa")
    @NotNull(message = "O nome é obrigatório")
    @Size(min = 3, max = 45, message = "O nome é obrigatório")
    private String firstName;

    @ApiModelProperty("Sobrenome da pessoa")
    @NotNull(message = "O sobrenome é obrigatório")
    @Size(min = 3, max = 45, message = "O sobrenome é obrigatório")
    private String lastName;

    @ApiModelProperty("Data de nascimento da pessoa")
    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    @ApiModelProperty("Documento da pessoa")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    @ApiModelProperty("Sexo")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty("Ideologia")
    @Enumerated(EnumType.STRING)
    private GenderIdeology genderIdeology;

    @ApiModelProperty("Nome do pai")
    private String fatherName;

    @ApiModelProperty("Nome da mãe")
    private String motherName;

    @ApiModelProperty("Número de telefone|celular principal")
    private String principalNumber;

    @ApiModelProperty("Número de telefone|celular segundário")
    private String secondaryNumber;

    @ApiModelProperty("Endereço")
    private AddressDTO address;

    @ApiModelProperty("Email")
    private String email;

    @ApiModelProperty("Foto do perfil")
    private String profilePhoto;

}
