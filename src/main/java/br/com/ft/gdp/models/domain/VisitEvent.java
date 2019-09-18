package br.com.ft.gdp.models.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.visitEvent.VisitEventRequestDTO;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Transient
	private Patient patient;
	
	@ManyToOne
	private EventType eventType;
	
	@ManyToOne
	private Responsible responsible;
	
	@Column(name = "URL_DOC")
	private String urlDoc;
	
	@Column(name = "TITULO")
	private String title;
	
	@Column(name = "OBSERVACOES")
	private String observations;
	
	@Column(name = "DH_EVENTO")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime eventDateTime;
	
	public VisitEvent(Patient patient,	EventType eventType, Responsible responsible,
			String urlDoc, String title, String observations) {
		this.patient = patient;
		this.eventType = eventType;
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
	@JsonIgnore
	public static VisitEvent getDomainFrom(VisitEventResponseDTO dto) {
		return new VisitEvent(dto.getId(), 
							   dto.getPatient(), 
							   dto.getEventType(), 
							   dto.getResponsible(), 
							   dto.getUrlDoc(), 
							   dto.getTitle(), 
							   dto.getObservations(), 
							   dto.getEventDateTime());
	}
	
	@Transient
	@JsonIgnore
	public static VisitEvent getDomainFrom(VisitEventRequestDTO dto) {
		return new VisitEvent(dto.getPatient(),
							   dto.getEventType(),
							   dto.getResponsible(),
							   dto.getUrlDoc(),
							   dto.getTitle(),
							   dto.getObservations());
	}

}
