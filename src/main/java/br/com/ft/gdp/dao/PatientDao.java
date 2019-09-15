package br.com.ft.gdp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Patient;

/**
 * 
* Classe PatientDao.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 15 de set de 2019
 */
@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {

    /**
     * @param id
     * @return
     */
    public Optional<Patient> findById(Long id);
    
    /**
     * @param cpf
     * @return
     */
    public Optional<Patient> findByCpf(String cpf);

}
