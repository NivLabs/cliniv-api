package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.repository.PersonRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Classe PersonService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class PersonService implements GenericService {

    @Autowired
    private PersonRepository dao;

    public Page<Person> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    public Person findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Pessoa com o identificador %s não encontrado", id)));
    }

    public Person update(Long id, Person entity) {
        Person person = findById(id);
        BeanUtils.copyProperties(entity, person, "id");
        if (StringUtils.isNullOrEmpty(entity.getCpf()))
            person.setCpf(null);
        person = dao.save(person);
        return person;
    }

    public void delete(Person entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public Person persist(Person entity) {
        entity.setId(null);
        if (StringUtils.isNullOrEmpty(entity.getCpf()))
            entity.setCpf(null);
        return dao.saveAndFlush(entity);
    }

    public Person findByCpf(String cpf) {
        if (StringUtils.isNullOrEmpty(cpf)) {
            throw new HttpException(HttpStatus.NOT_FOUND,
                    "O CPF informado é nulo, informe um CPF para que a consulta possa ser realizada");
        }
        return dao.findByCpf(cpf).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Pessoa com CPF: [%s] não encontrado", cpf)));
    }
}
