package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.dto.AnamnesisItemDTO;
import br.com.nivlabs.gp.models.enums.MetaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ANAMNESE_ITEM")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnamnesisItem extends BaseObjectWithId {
	private static final long serialVersionUID = -5324023359826888215L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "PERGUNTA")
	private String question;

	@Column(name = "TIPO")
	@Enumerated(EnumType.STRING)
	private MetaType metaType;

	@JsonIgnore
	public AnamnesisItemDTO getAnamnesisItemDTOFromDomain() {
		AnamnesisItemDTO domain = new AnamnesisItemDTO();
		BeanUtils.copyProperties(this, domain);
		return domain;
	}
}
