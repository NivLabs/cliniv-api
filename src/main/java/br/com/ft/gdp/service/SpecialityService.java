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
import br.com.ft.gdp.models.domain.Speciality;
import br.com.ft.gdp.models.dto.SpecialityDTO;
import br.com.ft.gdp.repository.SpecialityRepository;

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

    public Page<SpecialityDTO> searchEntityPage(Pageable pageRequest) {
        Page<Speciality> pageFromDb = dao.findAll(pageRequest);
        List<SpecialityDTO> convertedContent = new ArrayList<>();

        for (Speciality spec : pageFromDb.getContent()) {
            convertedContent.add(new SpecialityDTO(spec.getId(), spec.getName(), spec.getDescription()));
        }
        return new PageImpl<>(convertedContent, pageRequest, pageFromDb.getTotalElements());
    }

    public SpecialityDTO findById(Long id) {
        Speciality specFromDb = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Especialidade com identificador %s não encontrada", id)));

        SpecialityDTO convertedSpec = new SpecialityDTO();

        BeanUtils.copyProperties(specFromDb, convertedSpec);

        return convertedSpec;
    }

    public SpecialityDTO update(Long id, SpecialityDTO dto) {
        Speciality specToDb = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Especialidade com identificador %s não encontrada", id)));

        BeanUtils.copyProperties(dto, specToDb, "id");
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

    public SpecialityDTO persist(SpecialityDTO dto) {
        dto.setId(null);
        Speciality specToDb = new Speciality();
        BeanUtils.copyProperties(dto, specToDb);
        dao.save(specToDb);
        BeanUtils.copyProperties(specToDb, dto);
        return dto;
    }

}
