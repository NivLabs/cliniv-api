/**
 * 
 */
package br.com.ft.gdp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ft.gdp.models.domain.AnamnesisItem;

/**
 * AnamnesisItemDao.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
public interface AnamnesisItemDao extends JpaRepository<AnamnesisItem, Long>{
	
	/**
	 * @param id
	 * @return
	 */
	public Optional<AnamnesisItem> findById(Long id);	
}
