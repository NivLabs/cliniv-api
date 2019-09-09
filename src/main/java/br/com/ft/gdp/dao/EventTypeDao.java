package br.com.ft.gdp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.EventType;

/**
 * 
* Classe EventTypeDao.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 8 de set de 2019
 */
@Repository
public interface EventTypeDao extends JpaRepository<EventType, Long> {

    /**
<<<<<<< HEAD
     * @param id
=======
     * @param cpf
>>>>>>> 8e75f2d10e8d4b2efe3d95c905ebd35197db62d8
     * @return
     */
    public Optional<EventType> findById(Long id);

}
