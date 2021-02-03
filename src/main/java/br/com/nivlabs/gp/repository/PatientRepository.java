package br.com.nivlabs.gp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.repository.custom.patient.PatientRepositoryCustom;

/**
 * 
 * Classe PatientRepository.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, PatientRepositoryCustom {

    /**
     * 
     * Busca paciente por CPF informado
     * 
     * @param cpf
     * @return
     */
    @Query("from Patient where person.cpf = :cpf")
    public Optional<Patient> findByCpf(String cpf);

    /**
     * Busca informações por composição de chave
     * 
     * @param name
     * @param motherName
     * @return
     */
    @Query("from Patient where firstName like :name and motherName like :motherName")
    public List<Patient> findByComposition(String name, String motherName);

    public Optional<Patient> findBySusNumber(String susNumber);

}
