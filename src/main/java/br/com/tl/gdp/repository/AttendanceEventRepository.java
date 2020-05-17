package br.com.tl.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tl.gdp.models.domain.AttendanceEvent;

/**
 * Classe VisitEventRepository.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Repository
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Long> {

    /**
     * @param visitId
     * @return
     */
    List<AttendanceEvent> findByAttendanceId(Long visitId);

}
