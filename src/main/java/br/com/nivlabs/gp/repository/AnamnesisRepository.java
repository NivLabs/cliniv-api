package br.com.nivlabs.gp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.Attendance;

/**
 * AnamnesisRepository.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
@Repository
public interface AnamnesisRepository extends JpaRepository<Anamnesis, Long> {

    public List<Anamnesis> findByAttendance(Attendance attendance);

}
