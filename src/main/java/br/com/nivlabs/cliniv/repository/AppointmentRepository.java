package br.com.nivlabs.cliniv.repository;

import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.models.dto.AppointmentDTO;
import br.com.nivlabs.cliniv.repository.custom.schedule.AppointmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Query(value = """
            select new br.com.nivlabs.cliniv.models.dto.AppointmentDTO(
            a.id,
            a.patient.person.fullName,
            a.patient.person.cpf,
            a.professional.id,
            a.professional.person.fullName,
            a.appointmentDateAndTime,
            a.status)
            from  AGENDAMENTO a
            where a.patient.id = :patientId and a.appointmentDateAndTime >= :initDateTime
            """)
    List<AppointmentDTO> findUpcomingAppointmentsByPatientId(Long patientId, LocalDateTime initDateTime);


    @Query(value = """
            select new br.com.nivlabs.cliniv.models.dto.AppointmentDTO(
            a.id,
            a.patient.person.fullName,
            a.patient.person.cpf,
            a.professional.id,
            a.professional.person.fullName,
            a.appointmentDateAndTime,
            a.status)
            from  AGENDAMENTO a
            where a.patient.id = :patientId and a.appointmentDateAndTime >= :initDate and a.appointmentDateAndTime <= :endDate
            order by a.appointmentDateAndTime asc
            """)
    List<AppointmentDTO> findAppointmentsByPatientIdAndRangeDate(Long patientId, LocalDateTime initDate, LocalDateTime endDate);
}
