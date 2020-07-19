package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Institute;

/**
 * Classe InstituteRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Repository
public interface InstituteRepository extends JpaRepository<Institute, String> {
	
	@Modifying
	@Query("Update Institute i set i.companyLogo = ?1")
	public void setCompanyLogo(byte[] companyLogo);

}
