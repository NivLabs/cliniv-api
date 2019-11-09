package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe NewOrUpdatePatientAddressDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 18 de out de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientInfoAddressDTO implements Serializable {

    private static final long serialVersionUID = 4507712865290776284L;

    private String street;
    private String addressNumber;
    private String complement;
    private String postalCode;
    private String state;
    private String district;
    private String city;
}