package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.AnamnesisForm;

/**
 * Repositório para controle de formulários de anamnese
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface AnamnesisFormRepository extends JpaRepository<AnamnesisForm, Long> {

}
