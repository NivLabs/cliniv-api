package br.com.ft.gdp.models.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Patient.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Entity
@Table(name = "PACIENT")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends BaseObject {

	private static final long serialVersionUID = 4873898002597934236L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	private String firstName;

	@Column(name = "SOBRENOME")
	private String lastName;

	@Column(name = "RG")
	private String rg;

	@Column(name = "CPF")
	private String cpf;

	@Column(name = "DATA_NASCIMENTO")
	private Date bornDate;

	@ElementCollection
	@CollectionTable(name = "TELEFONE", foreignKey = @ForeignKey(name = "FK_TELEFONE_PACIENTE"))
	@Column(name = "TELEFONE")
	private Set<String> phones = new HashSet<>();

	@Column(name = "SEXO")
	private String gender;

	@Column(name = "NOME_COMP_PAI")
	private String fatherName;

	@Column(name = "NOME_COMP_MAE")
	private String motherName;
	
    @JsonIgnore
    public PatientDTO getPatientDTOFromDomain() {
    	PatientDTO dtoEntity = new PatientDTO();
    	
    	dtoEntity.setId(getId());
    	dtoEntity.setFirstName(getFirstName());
    	dtoEntity.setLastName(getLastName());
    	dtoEntity.setRg(getRg());
    	dtoEntity.setCpf(getCpf());
    	dtoEntity.setBornDate(getBornDate());
    	dtoEntity.setPhones(getPhones());
    	dtoEntity.setGender(getGender());
    	dtoEntity.setFatherName(getFatherName());
    	dtoEntity.setMotherName(getMotherName());
        
        return dtoEntity;
    }

}
