package br.com.ft.gdp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.models.dto.ProfileInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        return dao.saveAndFlush(entity);
    }

    public Person findByCpf(String cpf) {
        return dao.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException(String.format("Pessoa com CPF: [%s] não encontrado", cpf)));
    }

    /**
     * Atualiza ou cria informações de usuários
     * 
     * @param userId
     * @param entity
     * @return
     */
    public ProfileInfoDTO updateProfile(Long userId, ProfileInfoDTO entity) {
        Person person = findByUserId(userId);

        logger.info("Persistindo informações no banco de dados...");
        dao.save(getPersonFromUserInfo(person, entity));

        return entity;
    }

    /**
     * @param userId
     * @return
     */
    private Person findByUserId(Long userId) {
        return dao.findByUser(userId);
    }

    /**
     * 
     * Trata uma pessoa já existente na base à partir do DTO
     * 
     * @param entity
     * @return
     */
    private Person getPersonFromUserInfo(Person personToUpdate, ProfileInfoDTO entity) {
        BeanUtils.copyProperties(entity, personToUpdate, "id");
        logger.info("Copiando propriedades do objeto externo para a atualização :: {}", entity.toString());
        if (entity.getAddress() != null) {
            if (personToUpdate.getAddress() == null) {
                PersonAddress newAddress = new PersonAddress();
                newAddress.setPerson(personToUpdate);
                personToUpdate.setAddress(newAddress);
            }
            BeanUtils.copyProperties(entity.getAddress(), personToUpdate.getAddress());
        }

        if (entity.getDocument().getType() != DocumentType.CPF) {
            logger.error("Tipo de documento inválido :: Informado -> {} Esperado -> CPF", entity.getDocument().getType());
            throw new ValidationException("O tipo do documento deve ser CPF");
        }
        personToUpdate.setCpf(entity.getDocument().getValue());

        return personToUpdate;
    }
}
