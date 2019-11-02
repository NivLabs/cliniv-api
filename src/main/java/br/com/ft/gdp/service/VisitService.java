package br.com.ft.gdp.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Visit;
import br.com.ft.gdp.models.dto.NewVisitDTO;
import br.com.ft.gdp.repository.VisitRepository;

/**
 * 
 * Classe VisitService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Service
public class VisitService implements GenericService<Visit, Long> {

    @Autowired
    private VisitRepository dao;
    @Autowired
    private PatientService patientService;

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

    public void closeVisit(Long id) {
        Visit auxEntity = findById(id);
        auxEntity.setDateTimeExit(new Date());
        update(id, auxEntity);
    }

    /**
     * Realiza a criação de uma visita de paciente à partir do DTO
     * 
     * @param visitDto
     * @return
     */
    public Visit persistNewVisit(NewVisitDTO visitDto) {
        Patient savedPatient = patientService.findById(visitDto.getPatientId());

        Visit convertedVisit = new Visit();
        convertedVisit.setDateTimeEntry(new Date());
        convertedVisit.setReasonForEntry(visitDto.getReasonForEntry());
        convertedVisit.setPatient(savedPatient);

        return persist(convertedVisit);
    }

}
