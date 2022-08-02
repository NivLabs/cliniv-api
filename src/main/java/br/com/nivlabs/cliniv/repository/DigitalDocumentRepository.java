package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.DigitalDocument;

/**
 * 
 * Classe DigitalDocumentRepository.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 * @since 15 de set de 2019
 */
@Repository
public interface DigitalDocumentRepository extends JpaRepository<DigitalDocument, Long> {

}
