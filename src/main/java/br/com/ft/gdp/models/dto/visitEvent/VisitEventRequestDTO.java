package br.com.ft.gdp.models.dto.visitEvent;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.EventType;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.domain.VisitEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitEventRequestDTO.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitEventRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Informar o PACIENTE, é obrigatório")
	private Patient patient;
	@NotNull(message = "Informar o TIPO DO EVENTO, é obrigatório")
	private EventType eventType;
	@NotNull(message = "Informar o RESPONSÁVEL, é obrigatório")
	private Responsible responsible;
	
	@NotBlank(message = "Informar a URL DO DOCUMENTO, é obrigatório")
	private String urlDoc;
	
	@NotBlank(message = "Informar o TÍTULO, é obrigatório")
	@Size(min = 5, message = "Coloque um TÍTULO com no mínimo 5 caracteres")
	@Size(max = 30, message = "O TÍTULO deve ter no máximo 30 caracteres")
	private String title;
	
	@Size(min = 10, message = "A OBSERVAÇÃO deve conter ao menos 10 caracteres")
	@Size(max = 400, message = "A OBSERVAÇÃO deve conter no máximo 400 caracteres")
	private String observations;
	
	@JsonIgnore
	public static VisitEventRequestDTO getDtoFrom(VisitEvent domain) {
		return new VisitEventRequestDTO(domain.getPatient(),
										 domain.getEventType(),
										 domain.getResponsible(), 
										 domain.getUrlDoc(), 
										 domain.getTitle(), 
										 domain.getObservations());
	}

}