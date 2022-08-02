package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.tiss.Procedure;
import br.com.nivlabs.cliniv.repository.custom.procedureorevent.ProcedureRepositoryCustom;

/**
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long>, ProcedureRepositoryCustom {

}
