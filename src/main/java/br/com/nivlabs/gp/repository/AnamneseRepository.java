package br.com.nivlabs.gp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Anamnesis;

/**
 * AnamneseDao.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
@Repository
public interface AnamneseRepository extends JpaRepository<Anamnesis, Long> {

    /**
     * @param id
     * @return
     */
    public Optional<Anamnesis> findById(Long id);

}
