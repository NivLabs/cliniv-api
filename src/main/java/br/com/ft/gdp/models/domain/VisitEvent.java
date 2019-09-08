package br.com.ft.gdp.models.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.visitEvent.VisitEventResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitEvent.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Entity
@Table(name = "VISITA_EVENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class VisitEvent extends BaseObject {

	private static final long serialVersionUID = 8988537898462013276L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	private Patient patient;
	
	private EventType eventeType;
	
	private Responsible responsible;
	
	@Column(name = "URL_DOC")
	private String urlDoc;
	
	@Column(name = "TITULO")
	private String title;
	
	@Column(name = "OBSERVACOES")
	private String observations;
	
	@Column(name = "DH_EVENTO")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "ddMMyyyyHHmmss")
	private LocalDateTime eventDateTime;
	
	public VisitEvent(Patient patient, 	EventType eventeType, Responsible responsible,
			String urlDoc, String title, String observations) {
		this.patient = patient;
		this.eventeType = eventeType;
		this.responsible = responsible;
		this.urlDoc = urlDoc;
		this.title = title;
		this.observations = observations;
	}
	
	@PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.eventDateTime = now;
    }
	
	@Transient
	public static VisitEvent toDomain(VisitEventResponseDTO dto) {
		return new VisitEvent(dto.getId(), 
							   dto.getPatient(), 
							   dto.getEventType(), 
							   dto.getResponsible(), 
							   dto.getUrlDoc(), 
							   dto.getTitle(), 
							   dto.getObservations(), 
							   dto.getEventDateTime());
	}

}




