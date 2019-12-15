package br.com.ft.gdp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.SectorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Sector.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 dez, 2019
 */
@Entity
@Table(name = "SETOR")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sector extends BaseObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8491049323618565782L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SETOR")
    private Sector sector;

    
    @JsonIgnore
    public SectorDTO getSectorDTOFromDomain() {
    	SectorDTO dtoEntity = new SectorDTO();
        dtoEntity.setId(getId());
        dtoEntity.setDescription(getDescription());
        SectorDTO sectorDTOPai = dtoEntity.getSector();
		Sector sectorPai =  getSector();
		BeanUtils.copyProperties(sectorPai, sectorDTOPai);
		dtoEntity.setSector(sectorDTOPai);
        return dtoEntity;
    }
}
