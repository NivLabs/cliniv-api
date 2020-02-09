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
 * Classe ResponsibleDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResponsibleDTO implements Serializable {
    private static final long serialVersionUID = -5141572031863436326L;

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
