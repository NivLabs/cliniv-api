package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.ReportLayout;
import br.com.nivlabs.gp.models.domain.ReportLayoutParameter;

/**
 * 
 * Classe ReportRepository.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 16 de fev de 2021
 */
@Repository
public interface ReportParamRepository extends JpaRepository<ReportLayoutParameter, Long> {
    
    public void deleteByLayout(ReportLayout layout);
    

}
