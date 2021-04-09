package br.com.nivlabs.gp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.models.domain.DynamicFormAnswered;

@Repository
public interface DynamicFormAnsweredRepository extends JpaRepository<DynamicFormAnswered, Long> {

    @Modifying
    @Transactional
    void deleteByResponsibleId(Long responsibleId);

}
