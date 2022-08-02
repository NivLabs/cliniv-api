package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Sector;
import br.com.nivlabs.cliniv.repository.custom.sector.SectorRepositoryCustom;

/**
 * Classe SectorRepository.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 Dez, 2019
 */
@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>, SectorRepositoryCustom {

}
