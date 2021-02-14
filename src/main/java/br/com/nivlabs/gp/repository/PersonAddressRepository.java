package br.com.nivlabs.gp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.PersonAddress;

/**
 * 
 * Classe PersonAddressRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Repository
public interface PersonAddressRepository extends JpaRepository<PersonAddress, Long> {

    public List<PersonAddress> findByPersonId(Long personId);

}
