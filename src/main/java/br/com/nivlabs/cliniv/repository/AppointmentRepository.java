package br.com.nivlabs.cliniv.repository;

import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.repository.custom.schedule.AppointmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentRepositoryCustom {

    @Query(nativeQuery = true, value = "SELECT distinct(DAY(DATA_HORA)) FROM AGENDAMENTO a WHERE a.DATA_HORA >= :initDate AND a.DATA_HORA <= :finalDate")
    List<Integer> findDaysWithAppointment(LocalDate initDate, LocalDate finalDate);

    @Query(nativeQuery = true, value = "SELECT distinct(DAY(DATA_HORA)) FROM AGENDAMENTO a WHERE a.DATA_HORA >= :initDate AND a.DATA_HORA <= :finalDate AND a.ID_RESPONSAVEL = :professionalId")
    List<Integer> findDaysWithAppointmentAndProfessionalId(LocalDate initDate, LocalDate finalDate, Long professionalId);

}
