package br.com.nivlabs.cliniv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Attendance;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.repository.custom.attendance.AttendanceRepositoryCustom;

/**
 * 
 * Classe AttendanceRepository.java
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

    Optional<Attendance> findByPatientAndExitDateTimeIsNull(Patient patient);
}
