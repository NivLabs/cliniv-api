package br.com.ft.gdp.models.dto;

import java.io.Serializable;
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
 * Classe NewPatientDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de out de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientInfoDTO implements Serializable {
    private static final long serialVersionUID = 1575416178033511932L;

    private Long id;

    @NotNull(message = "Informe o NOME do paciente é obrigatório")
    @Size(min = 3, max = 45, message = "O nome do paciente deve conter ao menos três letras")
    private String firstName;

    @NotNull(message = "Informe o SOBRENOME do paciente é obrigatório")
    @Size(min = 3, max = 45, message = "O sobrenome do paciente deve conter ao menos três letras")
    private String lastName;

    @NotNull(message = "Informar a data de nascimento do paciente é obrigatório")
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

    private PatientInfoAddressDTO address;

}
