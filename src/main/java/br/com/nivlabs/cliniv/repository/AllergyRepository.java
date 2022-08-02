package br.com.nivlabs.cliniv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Allergy;
import br.com.nivlabs.cliniv.repository.custom.allergy.AllergyRepositoryCustom;

/**
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 */
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long>, AllergyRepositoryCustom {

    public Optional<Allergy> findByDescriptionIgnoreCase(String description);

}
