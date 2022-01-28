package br.com.nivlabs.gp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.enums.ParameterAliasType;
import br.com.nivlabs.gp.models.domain.Parameter;

/**
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    public Optional<Parameter> findByAlias(ParameterAliasType alias);
}
