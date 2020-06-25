package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
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
public class EventTypeService implements GenericService {

    @Autowired
    private EventTypeRepository dao;

    public Page<EventType> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    public EventType findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Tipo de evento com o identificador %s não encontrado", id)));

    }

    public EventType update(Long id, EventType entity) {
        EventType auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    public void delete(EventType entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        EventType auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    public EventType persist(EventType entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
