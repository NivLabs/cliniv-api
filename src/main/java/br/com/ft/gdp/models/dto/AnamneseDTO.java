package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Anamnese;
import br.com.ft.gdp.models.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data	
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode	
public class AnamneseDTO implements Serializable{
	private static final long serialVersionUID = -7700694137849034946L;
	
	private Long id;
	
	@NotBlank(message = "Informar o VISITANTE é obrigatório.")
	private Visit idVisit;
	
	private Patient idPatient;
	
	private AnamnesisItem idAnamnesisItem;
	
	private Response response;
	
	@JsonIgnore
	public Anamnese getAnamnesesDomainFromDTO() {
		Anamnese domain = new Anamnese();
		
		domain.setId(getId());
		domain.setIdAnamnesisItem(getIdAnamnesisItem());
		domain.setIdPatient(getIdPatient());
		domain.setIdVisit(getIdVisit());
		domain.setResponse(getResponse());
		
		return domain;
	}

}
