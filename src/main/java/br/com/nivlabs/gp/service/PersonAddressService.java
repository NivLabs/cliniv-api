package br.com.nivlabs.gp.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.PersonAddress;
import br.com.nivlabs.gp.models.domain.PersonAddress_;
import br.com.nivlabs.gp.repository.PersonAddressRepository;

/**
 * Classe PersonAddressService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class PersonAddressService implements BaseService {

    @Autowired
    private PersonAddressRepository dao;

    public Page<PersonAddress> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    public PersonAddress findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Endereço com o identificador %s não encontrado", id)));
    }

    public PersonAddress update(Long id, PersonAddress entity) {
        PersonAddress auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, PersonAddress_.ID);
        return dao.save(auxEntity);
    }

    public void delete(PersonAddress entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        PersonAddress auxEntity = findById(id);
        dao.delete(auxEntity);
    }

    public PersonAddress persist(PersonAddress entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    /**
     * @param id
     */
    public void deleteByPersonId(Long id) {
        List<PersonAddress> listToDelete = dao.findByPersonId(id);
        dao.deleteAll(listToDelete);
    }

}
