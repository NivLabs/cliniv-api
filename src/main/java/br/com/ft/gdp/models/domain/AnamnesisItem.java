package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	private AnamnesisItemDTO AnamnesisItemDTOFromDomain() {
		AnamnesisItemDTO domain = new AnamnesisItemDTO();
		
		return domain;
	}
}
