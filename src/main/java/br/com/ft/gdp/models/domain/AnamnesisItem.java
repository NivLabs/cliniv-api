package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.AnamnesisItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ANAMNESIS_ITEM")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnamnesisItem extends BaseObject{	
	private static final long serialVersionUID = -5324023359826888215L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "PERGUNTA")
	private String question;
	
	@Column(name = "RESPOSTA")
	private String response;
	
	@JsonIgnore
	public AnamnesisItemDTO getAnamnesisItemDTOFromDomain() {
		AnamnesisItemDTO domain = new AnamnesisItemDTO();
		domain.setId(getId());
		domain.setQuestion(getQuestion());
		domain.setResponse(getResponse());
		return domain;
	}
}
