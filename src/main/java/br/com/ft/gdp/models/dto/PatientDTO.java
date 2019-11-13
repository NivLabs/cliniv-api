package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.ft.gdp.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PatientDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientDTO implements Serializable {

    private static final long serialVersionUID = -1070682704153329772L;

    private Long id;

    private String firstName;

    private String lastName;

    private String rg;

    private String cpf;

    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    private String principalNumber;

    private Gender gender;
}
