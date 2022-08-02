package br.com.nivlabs.cliniv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.HealthPlan;

/**
 * 
 * Classe HealthPlanRepository.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 04 de agosto de 2020
 */
@Repository
public interface HealthPlanRepository extends JpaRepository<HealthPlan, Long> {

	public Optional<HealthPlan> findByPlanCode(Long planCode);

}
