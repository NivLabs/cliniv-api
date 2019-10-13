package br.com.ft.gdp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Speciality;

/**
 * Classe SpecialityDao.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 13 de out de 2019
 */
@Repository
public interface SpecialityDao extends JpaRepository<Speciality, Long> {

}
