package br.com.nivlabs.cliniv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.AttendanceEvent;

/**
 * Classe AttendanceEventRepository.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Repository
public interface AttendanceEventRepository extends JpaRepository<AttendanceEvent, Long> {

    public List<AttendanceEvent> findByAttendanceId(Long id);

}
