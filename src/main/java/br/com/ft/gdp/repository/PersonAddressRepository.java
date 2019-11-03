package br.com.ft.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.PersonAddress;

/**
 * 
 * Classe PersonRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Repository
public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {

    public List<PersonAddress> findByPersonId(Long personId);

}
