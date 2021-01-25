package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.EventType;

/**
 * 
* Classe EventTypeRepository.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 8 de set de 2019
 */
@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {

}
