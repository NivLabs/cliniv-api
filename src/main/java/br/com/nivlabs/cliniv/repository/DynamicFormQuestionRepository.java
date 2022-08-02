package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.DynamicFormQuestion;

@Repository
public interface DynamicFormQuestionRepository extends JpaRepository<DynamicFormQuestion, Long> {

}
