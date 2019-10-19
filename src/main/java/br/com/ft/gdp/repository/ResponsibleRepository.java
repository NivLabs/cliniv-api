package br.com.ft.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Responsible;

/**
 * Classe ResponsibleDao.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

}
