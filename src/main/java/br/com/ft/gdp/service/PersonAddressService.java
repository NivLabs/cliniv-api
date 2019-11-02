package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.repository.PersonAddressRepository;

/**
 * Classe PersonAddressService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class PersonAddressService implements GenericService<PersonAddress, Long> {

    @Autowired
    private PersonAddressRepository dao;

    @Override
    public Page<PersonAddress> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public PersonAddress findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Endereço com ID: [%s] não encontrado", id)));
    }

    @Override
    public PersonAddress update(Long id, PersonAddress entity) {
        PersonAddress auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(PersonAddress entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        PersonAddress auxEntity = findById(id);
        dao.delete(auxEntity);
    }

    @Override
    public PersonAddress persist(PersonAddress entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
