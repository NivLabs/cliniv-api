package br.com.ft.gdp.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.models.dto.NewOrUpdatePatientDTO;
import br.com.ft.gdp.repository.PatientRepository;
import br.com.ft.gdp.repository.PersonRepository;

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
    private PatientRepository dao;

    @Autowired
    private PersonRepository personDao;

    @Autowired
    private PersonAddressService addressService;

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
     * @param name
     * @param motherName
     * @param bornDate
     * @return
     */
    public List<Patient> findByComposition(String name, String motherName) {
        name = "%".concat(name).concat("%");
        motherName = "%".concat(motherName).concat("%");
        return dao.findByComposition(name, motherName);
    }

    /**
     * @param newPatient
     * @return
     */
    public Patient persistDto(NewOrUpdatePatientDTO newPatient) {
        Person personFromDto = personDao.findByCpf(newPatient.getCpf()).orElse(new Person());
        BeanUtils.copyProperties(newPatient, personFromDto);
        personDao.save(personFromDto);

        if (personFromDto.getListOfAddress() != null && personFromDto.getListOfAddress().isEmpty()) {
            PersonAddress address = new PersonAddress();
            BeanUtils.copyProperties(newPatient.getAddress(), address);
            address.setPersonId(personFromDto.getId());
            addressService.persist(address);

            personFromDto.getListOfAddress().add(address);
        }

        Patient patient = new Patient();
        BeanUtils.copyProperties(newPatient, patient, "address");
        patient.setPerson(personFromDto);

        return dao.save(patient);
    }

}
