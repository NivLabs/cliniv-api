package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.ft.gdp.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Classe UserInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 5123075975481132373L;

    private Long id;
    @NotNull(message = "O primeiro nome deve ser informado")
    private String firstName;
    @NotNull(message = "O sobrenome deve ser informado")
    private String lastName;
    private String fatherName;
    @NotNull(message = "O nome da mãe deve ser informado")
    private String motherName;
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;
    @NotNull(message = "O gênero deve ser informa")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private AddressDTO address;
    @Size(min = 8, message = "Informe um número de telefone válido. O número deve conter ao menos 8 dígitos.")
    private String principalPhone;
    @Size(min = 8, message = "Informe um número de telefone válido. O número deve conter ao menos 8 dígitos.")
    private String secondaryNumber;
    @NotNull(message = "A data de nascimento deve ser informada")
    private Date bornDate;
    private String observations;
    private String userName;
}
