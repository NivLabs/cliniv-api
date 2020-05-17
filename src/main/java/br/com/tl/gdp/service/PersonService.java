package br.com.tl.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tl.gdp.exception.ObjectNotFoundException;
import br.com.tl.gdp.models.domain.Person;
import br.com.tl.gdp.repository.PersonRepository;
import br.com.tl.gdp.util.StringUtils;

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
        if (StringUtils.isNullOrEmpty(entity.getCpf()))
            person.setCpf(null);
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
        if (StringUtils.isNullOrEmpty(entity.getCpf()))
            entity.setCpf(null);
        return dao.saveAndFlush(entity);
    }

    public Person findByCpf(String cpf) {
        if (StringUtils.isNullOrEmpty(cpf)) {
            throw new ObjectNotFoundException("O CPF informado é nulo, informe um CPF para que a consulta possa ser realizada");
        }
        return dao.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException(String.format("Pessoa com CPF: [%s] não encontrado", cpf)));
    }
}
