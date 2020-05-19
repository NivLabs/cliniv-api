package br.com.nivlabs.gp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.repository.custom.attendance.AttendanceRepositoryCustom;

/**
 * 
 * Classe VisitDao.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepositoryCustom {

    /**
     * @param patientId
     * @return
     */
    List<Attendance> findByPatient(Patient patient);

    Optional<Attendance> findByPatientAndDateTimeExitIsNull(Patient patient);
}
