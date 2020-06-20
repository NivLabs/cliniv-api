package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.enums.EntryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Visit.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "VISITA")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Attendance extends BaseObjectWithId {

	private static final long serialVersionUID = -2728953699232281599L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_PACIENTE")
	private Patient patient;

	@Column(name = "DH_ENTRADA")
	private LocalDateTime dateTimeEntry;

	@Column(name = "DH_SAIDA")
	private LocalDateTime dateTimeExit;

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "SETOR_ATUAL")
	private Sector currentSector;

	@Column(name = "TIPO_ENTRADA")
	@Enumerated(EnumType.STRING)
	private EntryType entryType;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "attendance")
	private List<AttendanceEvent> events = new ArrayList<>();

	@Column(name = "MOTIVO_ENTRADA")
	private String reasonForEntry;

	public Attendance(Long id) {
		this.id = id;
	}

	@PrePersist
	public void prePersist() {
		final LocalDateTime now = LocalDateTime.now();
		this.dateTimeEntry = now;
	}

}
