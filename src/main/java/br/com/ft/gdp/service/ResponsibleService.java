package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.dao.ResponsibleDao;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.dto.NewResponsibleDTO;
import br.com.ft.gdp.models.dto.ResponsibleDTO;

/**
 * Classe ResponsibleService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Service
public class ResponsibleService extends GenericService<Responsible, Long> {

    @Autowired
    private ResponsibleDao dao;

    @Autowired
    private SpecialityService specialityService;

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

    /**
     * Realiza a persistência à partir de um DTO
     * 
     * @param responsible
     * @return
     */
    public ResponsibleDTO persistDTO(NewResponsibleDTO responsible) {
        Responsible domain = new Responsible();
        domain.setName(responsible.getName());
        domain.setProfessionalIdentity(responsible.getProfessionalIdentity());
        responsible.getEspecialityIdsList().forEach(id -> {
            domain.getSpecialty().add(specialityService.findById(id));
        });

        return persist(domain).getResponsibleDTOFromDomain();
    }
}
