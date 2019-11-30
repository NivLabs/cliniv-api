package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@EqualsAndHashCode
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 5868142007921449692L;

    @NotNull(message = "Informe a rua do endereço")
    private String street;
    @NotNull(message = "Informe um número para o endereço")
    private String addressNumber;
    private String complement;
    @Size(min = 8, max = 8, message = "O CEP deve conter 8 dígitos, apenas números")
    private String postalCode;
    @NotNull(message = "Informe o estado do endereço")
    private String state;
    @NotNull(message = "Informe o bairro do endereço")
    private String neighborhood;
    @NotNull(message = "Informe a cidade do endereço")
    private String city;
}
