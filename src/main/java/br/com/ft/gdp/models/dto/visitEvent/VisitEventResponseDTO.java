package br.com.ft.gdp.models.dto.visitEvent;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * Classe VisitEventResponseDTO.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitEventResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Patient patient;
	private EventType eventType;
	private Responsible responsible;
	private String urlDoc;
	private String title;
	private String observations;
	private LocalDateTime eventDateTime;
	
	@JsonIgnore
	public static VisitEventResponseDTO toDTO(VisitEvent domain) {
		return new VisitEventResponseDTO(domain.getId(),
										  domain.getPatient(),
										  domain.getEventeType(),
										  domain.getResponsible(), 
										  domain.getUrlDoc(), 
										  domain.getTitle(), 
										  domain.getObservations(),
										  domain.getEventDateTime());
	}

}
