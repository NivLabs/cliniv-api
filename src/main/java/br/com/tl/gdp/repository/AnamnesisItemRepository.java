/**
 * 
 */
package br.com.tl.gdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tl.gdp.models.domain.AnamnesisItem;

/**
 * AnamnesisItemDao.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
public interface AnamnesisItemRepository extends JpaRepository<AnamnesisItem, Long>{
	
	/**
	 * @param id
	 * @return
	 */
	public Optional<AnamnesisItem> findById(Long id);	
}
