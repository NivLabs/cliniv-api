package br.com.ft.gdp.models.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.ft.gdp.models.enums.Gender;
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
public class PersonInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4735834196671409605L;

    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 3, max = 45, message = "O nome é obrigatório")
    private String firstName;

    @NotNull(message = "O sobrenome é obrigatório")
    @Size(min = 3, max = 45, message = "O sobrenome é obrigatório")
    private String lastName;

    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String fatherName;

    private String motherName;

    @Size(min = 8, message = "Informe um número de telefone válido. O número deve conter ao menos 8 dígitos.")
    private String principalNumber;

    @Size(min = 8, message = "Informe um número de telefone válido. O número deve conter ao menos 8 dígitos.")
    private String secondaryNumber;

    private AddressDTO address;

}
