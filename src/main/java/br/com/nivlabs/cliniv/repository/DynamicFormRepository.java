package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.DynamicForm;
import br.com.nivlabs.cliniv.repository.custom.anamnesisform.DynamicFormRepositoryCustom;

/**
 * Repositório para controle de formulários de anamnese
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface DynamicFormRepository extends JpaRepository<DynamicForm, Long>, DynamicFormRepositoryCustom {

}
