package br.com.ft.gdp.models.dto;

import java.io.Serializable;

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

    private String street;
    private String addressNumber;
    private String complement;
    private String postalCode;
    private String state;
    private String district;
    private String city;
}
