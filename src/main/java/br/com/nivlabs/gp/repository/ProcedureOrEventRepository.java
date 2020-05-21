package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.tiss.ProcedureOrEvent;
import br.com.nivlabs.gp.repository.custom.procedureorevent.ProcedureOrEventRepositoryCustom;

/**
 * 
 * @author viniciosarodrigues
 *
 */
@Repository
public interface ProcedureOrEventRepository extends JpaRepository<ProcedureOrEvent, Long>, ProcedureOrEventRepositoryCustom {

}
