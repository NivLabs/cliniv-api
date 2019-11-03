package br.com.ft.gdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.models.domain.PersonPhone;
import br.com.ft.gdp.models.domain.PersonPhoneId;
import br.com.ft.gdp.repository.PersonPhoneRepository;

/**
 * Classe PersonPhoneService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de nov de 2019
 */
@Service
public class PersonPhoneService implements GenericService<PersonPhone, PersonPhoneId> {

    @Autowired
    private PersonPhoneRepository repository;

    public PersonPhone saveOrupdate(PersonPhone entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(PersonPhone entity) {
        repository.delete(entity);
    }

    /**
     * @param id
     */
    public void deleteByPersonId(Long id) {
        repository.deleteByPersonId(id);
    }
}
