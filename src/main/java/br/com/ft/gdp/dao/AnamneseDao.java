package br.com.ft.gdp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ft.gdp.models.domain.Anamnese;

/**
 * AnamneseDao.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
public interface AnamneseDao extends JpaRepository<Anamnese, Long>{
	
	/**
	 * @param id
	 * @return
	 */
	public Optional<Anamnese> findById(Long id);

}
