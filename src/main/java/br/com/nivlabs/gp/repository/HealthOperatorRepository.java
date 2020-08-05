package br.com.nivlabs.gp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.repository.custom.healthoperator.HealthOperatorRepositoryCustom;

/**
 * Repositório de dados para Operadoras de planos de saúde
 * 
 * @author viniciosarodrigues
 *
 */
@Repository
public interface HealthOperatorRepository extends JpaRepository<HealthOperator, Long>, HealthOperatorRepositoryCustom {

    /**
     * Busca uma operadora de saúde baseada no código da ANS
     * 
     * @param ansCode - Código ANS
     * @return {@linkplain HealthOperator} Operadora de Saúde
     */
    public Optional<HealthOperator> findByAnsCode(String ansCode);

}
