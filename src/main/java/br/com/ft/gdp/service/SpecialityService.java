package br.com.ft.gdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Speciality;
import br.com.ft.gdp.repository.SpecialityRepository;

/**
 * Classe SpecialityService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 13 de out de 2019
 */
@Service
public class SpecialityService extends GenericService<Speciality, Long> {

    @Autowired
    private SpecialityRepository dao;

    @Override
    public Page<Speciality> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public Speciality findById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Especialidade com ID: [%s] não encontrado", id)));
    }

    @Override
    public Speciality update(Long id, Speciality entity) {
        findById(id);
        entity.setId(id);
        return dao.save(entity);
    }

    @Override
    public void delete(Speciality entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Speciality persist(Speciality entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
