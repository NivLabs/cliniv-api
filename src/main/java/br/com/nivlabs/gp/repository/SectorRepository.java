package br.com.nivlabs.gp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.repository.custom.sector.SectorRepositoryCustom;

/**
 * Classe SectorRepository.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 Dez, 2019
 */
@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>, SectorRepositoryCustom {

	List<Sector> findBySuperSectorIsNull();

}
