package br.com.ft.gdp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.dto.NewResponsibleDTO;
import br.com.ft.gdp.models.dto.ResponsibleDTO;
import br.com.ft.gdp.models.dto.ResponsibleInfoDTO;
import br.com.ft.gdp.repository.ResponsibleRepository;

/**
 * Classe ResponsibleService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Service
public class ResponsibleService {

    @Autowired
    private ResponsibleRepository dao;

    public Page<ResponsibleDTO> searchEntityPage(Pageable pageRequest) {
        Page<Responsible> pageOfResponsibles = dao.findAll(pageRequest);

        List<ResponsibleDTO> listOfResponsibleDTO = new ArrayList<>();

        pageOfResponsibles.forEach(responsible -> {
            ResponsibleDTO responsibleConverted = new ResponsibleDTO();
            responsibleConverted.setId(responsible.getId());
            BeanUtils.copyProperties(responsible.getPerson(), responsibleConverted, "id");
            listOfResponsibleDTO.add(responsibleConverted);
        });
        return new PageImpl<>(listOfResponsibleDTO, pageRequest, pageOfResponsibles.getTotalElements());
    }

    public ResponsibleInfoDTO findById(Long id) {
        Responsible responsibleFromDb = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));

        ResponsibleInfoDTO responsibleConverted = new ResponsibleInfoDTO();
        responsibleConverted.setId(responsibleFromDb.getId());
        BeanUtils.copyProperties(responsibleFromDb.getPerson(), responsibleConverted, "id");

        return responsibleConverted;
    }

    public ResponsibleInfoDTO update(Long id, ResponsibleInfoDTO responsible) {
        Responsible responsibleFromDb = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));
        BeanUtils.copyProperties(responsible, responsibleFromDb, "id");
        dao.save(responsibleFromDb);
        return responsible;
    }

    public void delete(Responsible entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        Responsible responsibleFromDb = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));
        dao.delete(responsibleFromDb);

    }

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
        domain.setProfessionalIdentity(responsible.getProfessionalIdentity());
        domain = persist(domain);

        ResponsibleDTO newResponsible = new ResponsibleDTO();
        newResponsible.setId(domain.getId());
        BeanUtils.copyProperties(domain.getPerson(), newResponsible, "id");

        return newResponsible;
    }
}
