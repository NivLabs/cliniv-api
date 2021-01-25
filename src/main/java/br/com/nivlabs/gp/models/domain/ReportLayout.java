package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithCreatedAt;

/**
 * Classe ReportLayout.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@Entity
@Table(name = "LAYOUT_RELATORIO")
public class ReportLayout extends BaseObjectWithCreatedAt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 545660687640626225L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "NOME")
	private String name;
	
    @Column(name = "DESCRICAO")
	private String description;
	
    @Column(name = "DATA")
	private LocalDateTime createdAt;
	
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_LAYOUT", referencedColumnName = "ID")
	private List<ReportLayoutParameter> params;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<ReportLayoutParameter> getParams() {
		return params;
	}

	public void setParams(List<ReportLayoutParameter> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ReportLayout [id=" + id + ", name=" + name + ", description=" + description + ", createdAt="
				+ createdAt + ", params=" + params + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportLayout other = (ReportLayout) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
