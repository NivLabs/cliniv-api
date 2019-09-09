package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.EventTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe EventType.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "TIPO_EVENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventType extends BaseObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8716334303463572525L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;
    
    @Column(name = "DESCRICAO")
    private String description;

    @JsonIgnore
    public EventTypeDTO getEventTypeDTOFromDomain() {
    	EventTypeDTO dtoEntity = new EventTypeDTO();

        dtoEntity.setId(getId());
        dtoEntity.setName(getName());
        dtoEntity.setDescription(getDescription());
        return dtoEntity;
    }
}
