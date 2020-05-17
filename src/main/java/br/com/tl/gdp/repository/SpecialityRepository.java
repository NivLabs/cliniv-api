package br.com.tl.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tl.gdp.models.domain.Speciality;

/**
 * Classe SpecialityDao.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 13 de out de 2019
 */
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

}
