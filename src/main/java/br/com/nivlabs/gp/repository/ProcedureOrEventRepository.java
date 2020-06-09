package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.repository.custom.procedureorevent.ProcedureOrEventRepositoryCustom;

/**
 * 
 * @author viniciosarodrigues
 *
 */
@Repository
public interface ProcedureOrEventRepository extends JpaRepository<Procedure, Long>, ProcedureOrEventRepositoryCustom {

}
