package br.com.ft.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Person;

/**
 * 
 * Classe PersonRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    /**
     * @param cpf
     * @return
     */
    public Optional<Person> findByCpf(String cpf);

    /**
     * @param rg
     * @return
     */
    public Optional<Person> findByRg(String rg);

    /**
     * @param name
     * @param motherName
     * @param bornDate
     * @return
     */
    @Query("from Patient where firstName like :name and motherName like :motherName")
    public List<Person> findByComposition(String name, String motherName);

}
