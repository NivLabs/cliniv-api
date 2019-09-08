package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.dao.ResponsibleDao;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.common.Responsible;

/**
 * Classe ResponsibleService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Service
public class ResponsibleService extends GenerciService<Responsible, Long> {

    @Autowired
    private ResponsibleDao dao;

    @Override
    public Page<Responsible> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public Responsible findById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));
    }

    @Override
    public Responsible update(Long id, Responsible entity) {
        Responsible auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(Responsible entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Responsible auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    @Override
    public Responsible persist(Responsible entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
