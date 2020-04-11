package br.com.ft.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.repository.custom.patient.PatientRepositoryCustom;

/**
 * 
 * Classe PatientDao.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, PatientRepositoryCustom {

    /**
     * @param cpf
     * @return
     */
    @Query("from Patient where person.cpf = :cpf")
    public Optional<Patient> findByCpf(String cpf);

    /**
     * @param name
     * @param motherName
     * @param bornDate
     * @return
     */
    @Query("from Patient where firstName like :name and motherName like :motherName")
    public List<Patient> findByComposition(String name, String motherName);

}
