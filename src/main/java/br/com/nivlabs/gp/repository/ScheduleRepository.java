package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Schedule;
import br.com.nivlabs.gp.repository.custom.schedule.ScheduleRepositoryCustom;

/**
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

}
