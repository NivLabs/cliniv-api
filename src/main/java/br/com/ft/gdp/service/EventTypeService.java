package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.dao.EventTypeDao;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.EventType;

/**
 * 
* Classe EventTypeService.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 8 de set de 2019
 */
@Service
public class EventTypeService extends GenerciService<EventType, Long> {

    @Autowired
    private EventTypeDao dao;

    @Override
    public Page<EventType> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public EventType findById(Long id) {
        return dao.findById(id)
<<<<<<< HEAD
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Tipo de evento com ID: [%s] não encontrado", id)));
=======
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));
>>>>>>> 8e75f2d10e8d4b2efe3d95c905ebd35197db62d8
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
