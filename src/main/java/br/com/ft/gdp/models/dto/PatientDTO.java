package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Patient;
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

	
	@NotBlank(message = "Informar a data de nascimento do paciente é obrigatório")
	private Date bornDate;

	private Set<String> phones = new HashSet<>();

	@NotBlank(message = "Informar o sexo do paciente é obrigatório")
	private String gender;

	private String fatherName;

	private String motherName;
	
    @JsonIgnore
    public Patient getPatientDomainFromDTO() {
    	Patient domain = new Patient();
    	
        domain.setId(getId());
        domain.setFirstName(getFirstName());
        domain.setLastName(getLastName());
        domain.setRg(getRg());
        domain.setCpf(getCpf());
        domain.setBornDate(getBornDate());
        domain.setPhones(getPhones());
        domain.setGender(getGender());
        domain.setFatherName(getFatherName());
        domain.setMotherName(getMotherName());
        
        return domain;
    }

}
