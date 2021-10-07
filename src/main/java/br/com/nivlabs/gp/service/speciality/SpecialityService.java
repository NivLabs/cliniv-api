package br.com.nivlabs.gp.service.speciality;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.SpecialityFilter;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Responsible_;
import br.com.nivlabs.gp.models.domain.Speciality;
import br.com.nivlabs.gp.models.domain.Speciality_;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.SpecialityDTO;
import br.com.nivlabs.gp.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.gp.repository.SpecialityRepository;

/**
 * Classe SpecialityService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 13 de out de 2019
 */
@Service
public class SpecialityService {

    @Autowired
    private SpecialityRepository dao;

    public Page<SpecialityDTO> searchEntityPage(SpecialityFilter filter, Pageable pageSettings) {
        return dao.resumedList(filter, pageSettings);
    }

    @Transactional
    public SpecialityInfoDTO findById(Long id) {
        Speciality specFromDb = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Especialidade com identificador %s não encontrada", id)));

        SpecialityInfoDTO convertedSpec = new SpecialityInfoDTO();

        BeanUtils.copyProperties(specFromDb, convertedSpec, "responsibles");

        specFromDb.getResponsibles().forEach(responsible -> {
            ResponsibleDTO responsibleDTO = new ResponsibleDTO();
            BeanUtils.copyProperties(responsible, responsibleDTO);
            BeanUtils.copyProperties(responsible.getPerson(), responsibleDTO, Responsible_.ID);
            convertedSpec.getResponsibles().add(responsibleDTO);
        });

        return convertedSpec;
    }

    @Transactional
    public SpecialityInfoDTO update(Long id, SpecialityInfoDTO dto) {
        Speciality specToDb = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Especialidade com identificador %s não encontrada", id)));

        BeanUtils.copyProperties(dto, specToDb, Speciality_.ID, Speciality_.RESPONSIBLES);
        dao.save(specToDb);
        BeanUtils.copyProperties(specToDb, dto);

        return dto;
    }

    public void delete(SpecialityDTO dto) {
        deleteById(dto.getId());
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Transactional
    public SpecialityInfoDTO persist(SpecialityInfoDTO dto) {
        dto.setId(null);
        Speciality specToDb = new Speciality();
        BeanUtils.copyProperties(dto, specToDb);
        dao.save(specToDb);
        BeanUtils.copyProperties(specToDb, dto);
        return dto;
    }

}
