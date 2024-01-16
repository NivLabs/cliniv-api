package br.com.nivlabs.cliniv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Person;

/**
 * Classe PersonRepository.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 18 de out de 2019
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Optional<Person> findByCpf(String cpf);

}
