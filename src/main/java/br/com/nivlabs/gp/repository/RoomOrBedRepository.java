package br.com.nivlabs.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.RoomOrBed;

@Repository
public interface RoomOrBedRepository extends JpaRepository<RoomOrBed, Long> {

}
