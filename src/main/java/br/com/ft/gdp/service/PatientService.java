package br.com.ft.gdp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.controller.filters.PatientFilters;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.PatientType;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.models.dto.AddressDTO;
import br.com.ft.gdp.models.dto.DocumentDTO;
import br.com.ft.gdp.models.dto.PatientDTO;
import br.com.ft.gdp.models.dto.PatientInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.PatientRepository;
import br.com.ft.gdp.util.StringUtils;

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
    public Page<PatientDTO> getListOfPatientInfo(PatientFilters filters, Pageable pageRequest) {
        return dao.resumedList(filters, pageRequest);
    }

    public PatientInfoDTO findByPateintId(Long id) {
        Patient patient = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com ID: [%s] não encontrado", id)));
        Person person = patient.getPerson();

        PatientInfoDTO patientInfo = new PatientInfoDTO();
        BeanUtils.copyProperties(person, patientInfo, "id");
        patientInfo.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(person.getAddress(), address);
            patientInfo.setAddress(address);
        }

        BeanUtils.copyProperties(patient, patientInfo);

        return patientInfo;

    }

    /**
     * Busca informações do cadatro de pessoa e insere no objeto de paciente
     * 
     * @param cpf
     * @return
     */
    private PatientInfoDTO findPersonByCpf(String cpf) {
        Person personFromDb = personService.findByCpf(cpf);

        PatientInfoDTO patientInfo = new PatientInfoDTO();
        BeanUtils.copyProperties(personFromDb, patientInfo, "id");
        patientInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

        if (personFromDb.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(personFromDb.getAddress(), address);
            patientInfo.setAddress(address);
        }

        return patientInfo;
    }

    /**
     * Busca paciente pelo CPF
     * 
     * @param cpf
     * @return
     */
    public PatientInfoDTO findByCpf(String cpf) {
        try {
            Patient patient = dao.findByCpf(cpf)
                    .orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com CPF: [%s] não encontrado", cpf)));
            Person personFromDb = patient.getPerson();

            PatientInfoDTO patientInfo = new PatientInfoDTO();
            BeanUtils.copyProperties(personFromDb, patientInfo, "id");
            patientInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

            if (personFromDb.getAddress() != null) {
                AddressDTO address = new AddressDTO();
                BeanUtils.copyProperties(personFromDb.getAddress(), address);
                patientInfo.setAddress(address);
            }
            BeanUtils.copyProperties(patient, patientInfo);
            return patientInfo;
        } catch (ObjectNotFoundException e) {
            return findPersonByCpf(cpf);
        }
    }

    /**
     * Busca um paciente baseado no código de cadastro do SUS
     * 
     * @param susNumber
     * @return
     */
    public PatientInfoDTO findBySusNumber(String susNumber) {
        Patient patient = dao.findBySusNumber(susNumber)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Paciente com cartão SUS de número %s não encontrado", susNumber)));
        Person personFromDb = patient.getPerson();

        PatientInfoDTO patientInfo = new PatientInfoDTO();
        BeanUtils.copyProperties(personFromDb, patientInfo, "id");
        patientInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

        if (personFromDb.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(personFromDb.getAddress(), address);
            patientInfo.setAddress(address);
        }
        BeanUtils.copyProperties(patient, patientInfo);
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
        Patient patient = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Paciente com ID: [%s] não encontrado", id)));

        checkSusCode(entity, patient);

        Person entityFromDb = patient.getPerson();

        checkDocument(entity, patient, entityFromDb);

        BeanUtils.copyProperties(entity, entityFromDb, "id");

        if (entity.getAddress() != null) {
            PersonAddress personAddress = null;
            AddressDTO address = entity.getAddress();
            if (entityFromDb.getAddress() == null)
                personAddress = new PersonAddress();
            else
                personAddress = entityFromDb.getAddress();
            BeanUtils.copyProperties(address, personAddress);
            personAddress.setPerson(entityFromDb);
            entityFromDb.setAddress(personAddress);
        }

        personService.update(entityFromDb.getId(), entityFromDb);

        BeanUtils.copyProperties(entity, patient, "id");
        handlePatientType(patient);
        dao.save(patient);

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
        if (entity.getDocument() != null && entity.getDocument().getType() != DocumentType.CPF) {
            logger.error("Tipo do documento inválido, informe um documento válido");
            throw new ValidationException("Tipo do documento inválido, informe um documento válido.");
        }

        checkDocument(entity);

        try {
            logger.info("Verificando se já existe um cadastro anexado ao documento informado...");
            personFromDb = personService.findByCpf(entity.getDocument().getValue());
        } catch (ObjectNotFoundException e) {
            logger.info("Nenhum cadastro encontrado :: Criando um novo cadastro de Pessoa no documento :: TIPO: {} | VALOR: {}",
                        entity.getDocument().getType(), entity.getDocument().getValue());
            personFromDb = new Person();
        }

        logger.info("Copiando as propriedades da requisição para o objeto de negócio...");
        BeanUtils.copyProperties(entity, personFromDb, "id");
        personFromDb.setCpf(entity.getDocument().getValue());

        logger.info("Verificando e-mail");
        if (entity.getAddress() != null) {
            AddressDTO address = entity.getAddress();
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
        newPatient.setPerson(personFromDb);
        newPatient.setSusNumber(entity.getSusNumber());
        newPatient.setCreatedAt(LocalDateTime.now());

        handlePatientType(newPatient);
        dao.save(newPatient);

        entity.setId(newPatient.getId());
        return entity;
    }

    /**
     * Trata tipo de paciente -> Identificado ou não identificado
     * 
     * @param newPatient
     */
    private void handlePatientType(Patient newPatient) {
        if (StringUtils.isNullOrEmpty(newPatient.getSusNumber()) && StringUtils.isNullOrEmpty(newPatient.getPerson().getMotherName())
                && StringUtils.isNullOrEmpty(newPatient.getPerson().getCpf())
                && StringUtils.isNullOrEmpty(newPatient.getPerson().getLastName())) {
            newPatient.setType(PatientType.NOT_IDENTIFIED);
        } else {
            newPatient.setType(PatientType.IDENTIFIED);
        }
    }

    /**
     * Checa se o paciente existe baseado no cpf
     * 
     * @param entity
     */
    private void patientCheckIfExistsByCpf(String cpf) {
        try {
            logger.info("Verificando se já há cadastro de paciente na base de dados :: CPF da busca -> {}",
                        cpf);
            PatientInfoDTO patient = findByCpf(cpf);
            if (patient != null && patient.getId() != null) {
                logger.warn("Paciente com o CPF {} já cadastrado.", cpf);
                throw new ValidationException("Paciente com o CPF informado já está cadastrado.");
            }
        } catch (ObjectNotFoundException e) {
            logger.info("Nenhum cadastro de paciente encontrado :: CPF da busca -> {}",
                        cpf);
            logger.info("Continuando cadastro de paciente...");
        }
    }

    /**
     * Checa documento para criação de novo paciente
     * 
     * @param entity
     */
    private void checkDocument(PatientInfoDTO entity) {
        if (entity.getDocument() != null && StringUtils.isNullOrEmpty(entity.getDocument().getValue())) {
            entity.getDocument().setValue(null);
        } else if (entity.getDocument() != null && !StringUtils.isNullOrEmpty(entity.getDocument().getValue()))
            patientCheckIfExistsByCpf(entity.getDocument().getValue());
    }

    /**
     * Valida documento para atualização
     * 
     * @param entity
     * @param patient
     * @param entityFromDb
     */
    private void checkDocument(PatientInfoDTO entity, Patient patient, Person entityFromDb) {
        if (entity.getDocument() != null && entity.getDocument().getType().equals(DocumentType.CPF)
                && StringUtils.isNullOrEmpty(entity.getDocument().getValue())) {
            entityFromDb.setCpf(entity.getDocument().getValue());
            Patient patientByCpf = dao.findByCpf(entity.getDocument().getValue()).orElse(null);
            if (patientByCpf != null && !patient.equals(patientByCpf)) {
                throw new ValidationException(
                        "Já existe um outro paciente utilizando este CPF, você não pode utilizar neste cadastro.");
            }
        }
    }

    /**
     * Valida Código SUS do paciente
     * 
     * @param entity
     * @param patient
     */
    private void checkSusCode(PatientInfoDTO entity, Patient patient) {
        if (entity.getSusNumber() != null && !entity.getSusNumber().equals(patient.getSusNumber())) {
            Patient patientBySusNumber = dao.findBySusNumber(entity.getSusNumber()).orElse(null);
            if (patientBySusNumber != null && !patient.equals(patientBySusNumber)) {
                throw new ValidationException(
                        "Já existe um outro paciente utilizando este código SUS, você não pode utilizar neste cadastro.");
            }
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
