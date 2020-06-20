package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.dto.AnamnesisDTO;
import br.com.nivlabs.gp.models.dto.AnamnesisItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ANAMNESE")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Anamnesis extends BaseObjectWithId {
	private static final long serialVersionUID = -4203582800741543902L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_VISITA")
	private Attendance attendance;

	@Column(name = "PERGUNTA")
	private String question;

	@Column(name = "RESPOSTA")
	private String response;

	@JsonIgnore
	public AnamnesisDTO getAnamneseDTOFromDomain() {
		AnamnesisDTO dto = new AnamnesisDTO();

		dto.setId(id);
		dto.setAnamnesisItem(new AnamnesisItemDTO(null, question, null));
		dto.setAttendanceId(attendance.getId());
		dto.setResponse(response);

		return dto;
	}

	@PreUpdate
	protected void blockUpdate() {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED,
				"Método não autorizado -> Não é permitido alterar uma anamnese");
	}
}
