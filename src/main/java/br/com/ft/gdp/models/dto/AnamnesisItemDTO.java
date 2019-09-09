package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import br.com.ft.gdp.models.domain.AnamnesisItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AnamnesisItemDTO implements Serializable{
	private static final long serialVersionUID = -1666755500493520346L;
	
	private Long id;
	
	private String question;
	
	private String response;
	
	public AnamnesisItem getAnamnesisItemDomainFromDTO() {
		AnamnesisItem dto = new AnamnesisItem();
		
		dto.setId(getId());
		dto.setQuestion(getQuestion());
		dto.setResponse(getResponse());
		
		return dto;
	}

}
