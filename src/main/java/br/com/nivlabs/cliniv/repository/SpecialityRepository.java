package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Speciality;
import br.com.nivlabs.cliniv.repository.custom.speciality.SpecialityRepositoryCustom;

/**
 * Classe SpecialityDao.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 13 de out de 2019
 */
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long>, SpecialityRepositoryCustom {

}
