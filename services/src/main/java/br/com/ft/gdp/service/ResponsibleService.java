package br.com.ft.gdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.dao.ResponsibleDao;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Responsible update(Long id, Responsible entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Responsible entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Responsible persist(Responsible entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
