package br.com.ft.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Visit;

/**
 * 
 * Classe VisitDao.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    /**
     * @param patientId
     * @return
     */
    List<Visit> findByPatient(Patient patient);

}
