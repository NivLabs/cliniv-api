package br.com.nivlabs.cliniv.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.repository.custom.schedule.AppointmentRepositoryCustom;

/**
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentRepositoryCustom {

    @Query(nativeQuery = true, value = "SELECT distinct(DAY(DATA_HORA)) FROM AGENDAMENTO a WHERE a.DATA_HORA >= :initDate AND a.DATA_HORA <= :finalDate")
    public List<Integer> findDaysWithAppointment(LocalDate initDate, LocalDate finalDate);
}
