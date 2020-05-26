package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.ObjectNotFoundException;
import br.com.nivlabs.gp.models.domain.EventType;
import br.com.nivlabs.gp.repository.EventTypeRepository;

/**
 * 
* Classe EventTypeService.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 8 de set de 2019
 */
@Service
public class EventTypeService implements GenericService<EventType, Long> {

    @Autowired
    private EventTypeRepository dao;

    @Override
    public Page<EventType> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public EventType findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Tipo de evento com o identificador %s não encontrado", id)));

    }

    @Override
    public EventType update(Long id, EventType entity) {
        EventType auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(EventType entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        EventType auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    @Override
    public EventType persist(EventType entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
