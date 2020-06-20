
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
@Table(name = "TABELA_CBO")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CBOTable extends BaseObjectWithId {

	private static final long serialVersionUID = -6730805701015543750L;

	@Id
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;
}
