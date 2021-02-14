package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.repository.custom.anamnesisform.AnamnesisFormRepositoryCustom;

/**
 * Repositório para controle de formulários de anamnese
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface DynamicFormRepository extends JpaRepository<DynamicForm, Long>, AnamnesisFormRepositoryCustom {

}
