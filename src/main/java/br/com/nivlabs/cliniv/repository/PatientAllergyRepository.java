package br.com.nivlabs.cliniv.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.PatientAllergy;
import br.com.nivlabs.cliniv.models.domain.PatientAllergyID;

/**
 * 
 * Classe PatientAllergyRepository.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Repository
public interface PatientAllergyRepository extends JpaRepository<PatientAllergy, PatientAllergyID> {

    @Transactional
    public void deleteByPatientId(Long patientId);

}
