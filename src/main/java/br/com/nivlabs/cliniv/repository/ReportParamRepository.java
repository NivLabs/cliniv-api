package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.nivlabs.cliniv.models.domain.ReportLayout;
import br.com.nivlabs.cliniv.models.domain.ReportLayoutParameter;

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

    @Transactional
    public void deleteByLayout(ReportLayout layout);

}
