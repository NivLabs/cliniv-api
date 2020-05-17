package br.com.tl.gdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tl.gdp.models.domain.Responsible;
import br.com.tl.gdp.repository.custom.responsible.ResponsibleRepositoryCustom;

/**
 * Classe ResponsibleDao.java
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
