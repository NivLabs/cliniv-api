package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.repository.PersonRepository;

/**
 * Classe PersonService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class PersonService implements GenericService<Person, Long> {

    @Autowired
    private PersonRepository dao;

    @Autowired
    private PersonAddressService addressService;

    @Autowired
    private PersonPhoneService phoneService;

    @Override
    public Page<Person> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public Person findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Pessoa com ID: [%s] não encontrado", id)));
    }

    @Override
    public Person update(Long id, Person entity) {
        Person person = findById(id);
        BeanUtils.copyProperties(entity, person, "id");
        person = dao.save(person);

        return person;
    }

    @Override
    public void delete(Person entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Person persist(Person entity) {
        entity.setId(null);
        Person person = dao.save(entity);
        if (!person.getListOfAddress().isEmpty()) {
            PersonAddress address = person.getListOfAddress().get(0);
            address.setPersonId(person.getId());
            addressService.persist(address);
        }
        if (!person.getPhones().isEmpty()) {
            person.getPhones().forEach(phone -> {
                phone.setPersonId(person.getId());
                phoneService.saveOrupdate(phone);
            });
        }

        return person;
    }

    public Person findByCpf(String cpf) {
        return dao.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException(String.format("Pessoa com CPF: [%s] não encontrado", cpf)));
    }
}
