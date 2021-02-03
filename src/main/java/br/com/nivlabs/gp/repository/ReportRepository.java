package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.ReportLayout;

/**
 * 
 * Classe ReportRepository.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 24 de jan de 2021
 */
@Repository
public interface ReportRepository extends JpaRepository<ReportLayout, Long> {


}
