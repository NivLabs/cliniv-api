package br.com.ft.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Attendance;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.repository.custom.attendance.AttendanceRepositoryCustom;

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
