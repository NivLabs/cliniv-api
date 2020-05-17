package br.com.tl.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tl.gdp.models.domain.DigitalDocument;

/**
 * 
 * Classe PatientDao.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Repository
public interface DigitalDocumentRepository extends JpaRepository<DigitalDocument, Long> {

}
