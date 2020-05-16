package br.com.ft.gdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
