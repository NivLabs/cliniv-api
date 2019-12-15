package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Sector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe SectorDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 13 de dez de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SectorDTO implements Serializable {

	private static final long serialVersionUID = -8018406138528606923L;

	private Long id;

	private String description;
	
	private SectorDTO sector;

	@JsonIgnore
	public Sector getSectorDomainFromDTO() {
		Sector domain = new Sector();
		domain.setId(id);
		domain.setDescription(description);
		SectorDTO sectorDTOPai = getSector();
		Sector sectorPai = domain.getSector();
		BeanUtils.copyProperties(sectorDTOPai, sectorPai);
		domain.setSector(sectorPai);
		return domain;
	}
}
