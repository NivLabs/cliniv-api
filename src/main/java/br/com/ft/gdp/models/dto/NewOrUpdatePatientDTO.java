package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Patient;
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
public class NewOrUpdatePatientDTO implements Serializable {
    private static final long serialVersionUID = 1575416178033511932L;

    @NotBlank(message = "Informar o NOME do paciente é obrigatório")
    @Size(min = 3, max = 45, message = "O nome do paciente deve conter ao menos três letras")
    private String firstName;

    @NotBlank(message = "Informar o SOBRENOME do paciente é obrigatório")
    @Size(min = 3, max = 45, message = "O sobrenome do paciente deve conter ao menos três letras")
    private String lastName;

    private String rg;

    @NotBlank(message = "Informar o CPF do paciente é obrigatório")
    @Size(min = 11, max = 11, message = "O cpf do paciente deve conter 11 digitos")
    private String cpf;

    @NotNull(message = "Informar a data de nascimento do paciente é obrigatório")
    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    @NotBlank(message = "Informar o sexo do paciente é obrigatório")
    private String gender;

    private String fatherName;

    private String motherName;

    @JsonIgnore
    public Patient getPatientDomainFromDTO() {
        Patient domain = new Patient();

        domain.setFirstName(getFirstName());
        domain.setLastName(getLastName());
        domain.setRg(getRg());
        domain.setCpf(getCpf());
        domain.setBornDate(getBornDate());
        domain.setGender(getGender());
        domain.setFatherName(getFatherName());
        domain.setMotherName(getMotherName());

        return domain;
    }

}
