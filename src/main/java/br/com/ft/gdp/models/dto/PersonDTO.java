package br.com.ft.gdp.models.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.ft.gdp.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PersonDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PersonDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -1442279002298984040L;

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
