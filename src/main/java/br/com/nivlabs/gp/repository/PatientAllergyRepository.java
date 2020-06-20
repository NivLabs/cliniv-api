package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.PatientAllergy;
import br.com.nivlabs.gp.models.domain.PatientAllergyID;

/**
 * 
 * Classe PatientDao.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Repository
public interface PatientAllergyRepository extends JpaRepository<PatientAllergy, PatientAllergyID> {

    public void deleteByPatientId(Long patientId);

}
