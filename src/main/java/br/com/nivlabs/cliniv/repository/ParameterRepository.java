package br.com.nivlabs.cliniv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.enums.ParameterAliasType;
import br.com.nivlabs.cliniv.models.domain.Parameter;

/**
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    public Optional<Parameter> findByAlias(ParameterAliasType alias);
}
