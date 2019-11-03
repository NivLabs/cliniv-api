package br.com.ft.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.PersonPhone;
import br.com.ft.gdp.models.domain.PersonPhoneId;

/**
 * Classe PersonPhoneRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de nov de 2019
 */
@Repository
public interface PersonPhoneRepository extends JpaRepository<PersonPhone, PersonPhoneId> {

    /**
     * Deleta todos os telefones baseados no identificador da pessoa
     * 
     * @param id
     */
    public void deleteByPersonId(Long id);

}
