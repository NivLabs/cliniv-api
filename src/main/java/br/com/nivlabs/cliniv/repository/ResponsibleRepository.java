package br.com.nivlabs.cliniv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.repository.custom.responsible.ResponsibleRepositoryCustom;

/**
 * Classe ResponsibleRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long>, ResponsibleRepositoryCustom {

    /**
     * @param cpf
     * @return
     */
    @Query("from Responsible where person.cpf = :cpf")
    public Optional<Responsible> findByCpf(String cpf);

}
