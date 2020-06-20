package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe AddressDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Endereço")
public class AddressDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 5868142007921449692L;

    @ApiModelProperty("Rua do endereço")
    @NotNull(message = "Informe a rua do endereço")
    private String street;

    @ApiModelProperty("Número do imóvel")
    @NotNull(message = "Informe um número para o endereço")
    private String addressNumber;

    @ApiModelProperty("Complemento do endereço, se houver")
    private String complement;

    @ApiModelProperty("CEP do endereço")
    @Size(min = 8, max = 8, message = "O CEP deve conter 8 dígitos, apenas números")
    private String postalCode;

    @ApiModelProperty("Estado")
    @NotNull(message = "Informe o estado do endereço")
    private String state;

    @ApiModelProperty("Bairro")
    @NotNull(message = "Informe o bairro do endereço")
    private String neighborhood;

    @ApiModelProperty("Cidade")
    @NotNull(message = "Informe a cidade do endereço")
    private String city;
}
