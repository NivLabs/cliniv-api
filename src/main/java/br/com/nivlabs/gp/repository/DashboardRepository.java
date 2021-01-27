package br.com.nivlabs.gp.repository;

import java.time.ZonedDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Person;

/**
 * Repositório criado para realizar consultas para o Dashboard TODO: Melhorar esta estrutura em breve...
 * 
 * @author viniciosarodrigues
 *
 */
@Repository
public interface DashboardRepository extends JpaRepository<Person, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(P.ID) FROM PACIENTE P WHERE DATE_FORMAT(P.DATA_CRIACAO,'%Y-%m-%d') = DATE_FORMAT(:date, '%Y-%m-%d')")
    public long getNewPatientsCount(ZonedDateTime date);

    @Query(nativeQuery = true, value = "SELECT COUNT(V.ID) FROM VISITA V WHERE V.DH_SAIDA IS NOT NULL AND DATE_FORMAT(V.DH_SAIDA, '%Y-%m-%d') = DATE_FORMAT(:date, '%Y-%m-%d')")
    public long getMedicalCareProvidedCount(ZonedDateTime date);

    @Query(nativeQuery = true, value = "SELECT COUNT(AGEND_CANCELAD.ID) FROM AGENDAMENTO AGEND_CANCELAD WHERE DATE_FORMAT(AGEND_CANCELAD.DATA_HORA, '%Y-%m-%d') = DATE_FORMAT(:date, '%Y-%m-%d') AND AGEND_CANCELAD.SITUACAO like 'CANCELED'")
    public long getCanceledScheduleCount(ZonedDateTime date);

    @Query(nativeQuery = true, value = "SELECT COUNT(AGEND_CONFIRMED.ID) FROM AGENDAMENTO AGEND_CONFIRMED WHERE DATE_FORMAT(AGEND_CONFIRMED.DATA_HORA, '%Y-%m-%d') = DATE_FORMAT(:date, '%Y-%m-%d') AND AGEND_CONFIRMED.SITUACAO like 'CONFIRMED'")
    public long getConfirmedScheduleCount(ZonedDateTime date);

}
