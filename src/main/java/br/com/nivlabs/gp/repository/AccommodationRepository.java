package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

}
