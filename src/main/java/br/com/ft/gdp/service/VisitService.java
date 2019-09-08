package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.dao.VisitDao;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Visit;

/**
 * 
* Classe VisitService.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 8 de set de 2019
 */
@Service
public class VisitService extends GenerciService<Visit, Long> {

    @Autowired
    private VisitDao dao;

    @Override
    public Page<Visit> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public Visit findById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Visita com ID: [%s] não encontrado", id)));
    }

    @Override
    public Visit update(Long id, Visit entity) {
        Visit auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(Visit entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Visit auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    @Override
    public Visit persist(Visit entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
