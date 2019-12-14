package br.com.ft.gdp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ft.gdp.models.domain.VisitEvent;

/**
 * Classe VisitEventRepository.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Repository
public interface VisitEventRepository extends JpaRepository<VisitEvent, Long> {

    @Transactional(readOnly = true)
    Page<VisitEvent> findByPatientId(Long id, Pageable pageable);

    /**
     * @param visitId
     * @return
     */
    List<VisitEvent> findByVisitId(Long visitId);

}
