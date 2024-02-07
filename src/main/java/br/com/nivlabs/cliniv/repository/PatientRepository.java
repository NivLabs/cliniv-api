package br.com.nivlabs.cliniv.repository;

import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.repository.custom.patient.PatientRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe PatientRepository.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 * @since 15 de set de 2019
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, PatientRepositoryCustom {

    @Query("from Patient where person.cpf = :cpf")
    Optional<Patient> findByCpf(String cpf);

    Optional<Patient> findByCnsNumber(String cnsNumber);

}
