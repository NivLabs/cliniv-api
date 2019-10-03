package br.com.ft.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.dao.PatientDao;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Patient;

/**
 * 
 * Classe PatientService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Service
public class PatientService extends GenericService<Patient, Long> {

    @Autowired
    private PatientDao dao;

    @Override
    public Page<Patient> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public Patient findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com ID: [%s] não encontrado", id)));

    }

    public Patient findByCpf(String cpf) {
        return dao.findByCpf(cpf)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com cpf: [%s] não encontrado", cpf)));

    }

    @Override
    public Patient update(Long id, Patient entity) {
        Patient auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(Patient entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Patient auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    @Override
    public Patient persist(Patient entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    /**
     * @param document
     * @return
     */
    public Patient findByRg(String rg) {
        return dao.findByRg(rg).orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com rg: [%s] não encontrado", rg)));
    }

}
