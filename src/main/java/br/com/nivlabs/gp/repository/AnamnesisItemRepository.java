/**
 * 
 */
package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nivlabs.gp.models.domain.DynamicFormQuestion;

/**
 * AnamnesisItemRepository.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
public interface AnamnesisItemRepository extends JpaRepository<DynamicFormQuestion, Long> {

}
