package br.com.ft.gdp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ft.gdp.models.domain.VisitEvent;

/**
 * Classe VisitEventResponseDTO.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Repository
public interface VisitEventDao extends JpaRepository<VisitEvent, Long> {
	
	@Transactional(readOnly = true)
	Page<VisitEvent> findByPatientId(Long id, Pageable pageable);
	
}
