package br.com.ft.gdp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.models.dto.PatientDTO;
import br.com.ft.gdp.models.dto.PatientInfoAddressDTO;
import br.com.ft.gdp.models.dto.PatientInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.PatientRepository;

/**
 * 
 * Classe PatientService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Service
public class PatientService implements GenericService<Patient, Long> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientRepository dao;

    @Autowired
    private PersonService personService;

    /**
     * Busca uma página de pacientes
     * 
     * @param pageRequest
     * @return
     */
    public Page<PatientDTO> getListOfPatientInfo(Pageable pageRequest) {
        Page<Patient> pageOfPatient = dao.findAll(pageRequest);

        List<PatientDTO> listOfPatientDTO = new ArrayList<>();

        pageOfPatient.forEach(patient -> {
            PatientDTO patientConverted = new PatientDTO();
            patientConverted.setId(patient.getId());
            BeanUtils.copyProperties(patient.getPerson(), patientConverted, "id");
            listOfPatientDTO.add(patientConverted);
        });
        return new PageImpl<>(listOfPatientDTO, pageRequest, pageOfPatient.getTotalElements());
    }

    @Override
    public Patient findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com ID: [%s] não encontrado", id)));

    }

    public PatientInfoDTO findByCpf(String cpf) {
        Patient patient = dao.findByCpf(cpf)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com cpf: [%s] não encontrado", cpf)));
        Person person = patient.getPerson();
        PatientInfoDTO patientInfo = new PatientInfoDTO();

        patientInfo.setId(patient.getId());
        BeanUtils.copyProperties(person, patient, "id");

        return patientInfo;

    }

    /**
     * Atualiza paciente
     * 
     * @param id
     * @param entity
     * @return
     */
    public PatientInfoDTO update(Long id, PatientInfoDTO entity) {
        Person entityFromDb = findById(id).getPerson();

        BeanUtils.copyProperties(entity, entityFromDb, "id");

        if (entity.getAddress() != null) {
            PatientInfoAddressDTO address = entity.getAddress();
            PersonAddress personAddress = new PersonAddress();
            BeanUtils.copyProperties(address, personAddress);
            personAddress.setPerson(entityFromDb);
            entityFromDb.setAddress(personAddress);
        }

        personService.update(id, entityFromDb);

        return entity;
    }

    /**
     * Deleta paciente
     */
    @Override
    public void delete(Patient entity) {
        deleteById(entity.getId());
    }

    /**
     * Deleta Paciente por ID
     */
    @Override
    public void deleteById(Long id) {
        Patient auxEntity = findById(id);
        dao.delete(auxEntity);
    }

    /**
     * Cria um novo paciente
     */
    public PatientInfoDTO persist(PatientInfoDTO entity) {
        entity.setId(null);
        Person personFromDb = new Person();
        if (entity.getDocument() == null || (entity.getDocument() != null && entity.getDocument().getType() != DocumentType.CPF)) {
            logger.error("Tipo do documento inválido, informe um documento válido");
            throw new ValidationException("Tipo do documento inválido, informe um documento válido.");
        }
        patientCheckIfExists(entity);
        if (entity.getDocument() != null && entity.getDocument().getValue() != null) {
            try {
                logger.info("Verificando se já existe um cadastro anexado ao documento informado...");
                personFromDb = personService.findByCpf(entity.getDocument().getValue());
            } catch (ObjectNotFoundException e) {
                logger.info("Nenhum cadastro encontrado :: Criando um novo cadastro de Pessoa no documento :: TIPO: {} | VALOR: {}",
                            entity.getDocument().getType(), entity.getDocument().getValue());
                personFromDb = new Person();
            }
        }

        logger.info("Copiando as propriedades da requisição para o obijeto de negócio...");
        BeanUtils.copyProperties(entity, personFromDb, "id");
        personFromDb.setCpf(entity.getDocument().getValue());

        logger.info("Verificando e-mail");
        if (entity.getAddress() != null) {
            PatientInfoAddressDTO address = entity.getAddress();
            PersonAddress personAddress = new PersonAddress();
            if (personFromDb.getAddress() != null)
                personAddress = personFromDb.getAddress();
            BeanUtils.copyProperties(address, personAddress, "id");
            personAddress.setPerson(personFromDb);
            personFromDb.setAddress(personAddress);
        }

        if (personFromDb.getId() != null)
            personService.update(personFromDb.getId(), personFromDb);
        else
            personService.persist(personFromDb);

        Patient newPatient = new Patient();
        newPatient.setId(null);
        newPatient.setPerson(personFromDb);
        dao.save(newPatient);

        return entity;
    }

    /**
     * @param entity
     */
    private void patientCheckIfExists(PatientInfoDTO entity) {
        try {
            logger.info("Verificando se já há cadastro de paciente na base de dados :: CPF da busca -> {}",
                        entity.getDocument().getValue());
            if (findByCpf(entity.getDocument().getValue()) != null) {
                logger.warn("Paciente com o CPF {} já cadastrado.", entity.getDocument().getValue());
                throw new ValidationException("Paciente com o CPF informado já está cadastrado.");
            }
        } catch (ObjectNotFoundException e) {
            logger.info("Nenhum cadastro de paciente encontrado :: CPF da busca -> {}",
                        entity.getDocument().getValue());
            logger.info("Continuando cadastro de paciente...");
        }
    }

    /**
     * Busca por chave composta
     * 
     * @param name
     * @param motherName
     * @return
     */
    public List<Patient> findByComposition(String name, String motherName) {
        name = "%".concat(name).concat("%");
        motherName = "%".concat(motherName).concat("%");
        return dao.findByComposition(name, motherName);
    }

}
