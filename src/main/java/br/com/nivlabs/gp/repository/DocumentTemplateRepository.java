package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.DocumentTemplate;
import br.com.nivlabs.gp.models.domain.DocumentTemplatePK;
import br.com.nivlabs.gp.repository.custom.documenttemplate.DocumentTemplateRepositoryCustom;

/**
 * 
 * Classe DigitalDocumentRepository.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 * @since 18 de abr de 2022
 */
@Repository
public interface DocumentTemplateRepository extends JpaRepository<DocumentTemplate, DocumentTemplatePK>, DocumentTemplateRepositoryCustom {

}
