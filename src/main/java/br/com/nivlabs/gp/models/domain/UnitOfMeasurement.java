package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIDADE_MEDIDA")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasurement extends BaseObjectWithId {

	private static final long serialVersionUID = -5469261685230682235L;

	@Id
	private Long id;

	@Column(name = "DESCRICAO")
	private String description;
}
