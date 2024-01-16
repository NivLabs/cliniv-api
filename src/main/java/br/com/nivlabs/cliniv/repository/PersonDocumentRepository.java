package br.com.nivlabs.cliniv.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.PersonDocument;
import br.com.nivlabs.cliniv.models.domain.PersonDocumentPK;

@Repository
public interface PersonDocumentRepository extends JpaRepository<PersonDocument, PersonDocumentPK> {

    @Modifying
    @Transactional
    public void deleteByPerson(Person person);
}
