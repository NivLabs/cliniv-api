package br.com.nivlabs.cliniv.service.patient.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.enums.PatientType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.HealthPlan;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.PersonAddress;
import br.com.nivlabs.cliniv.models.domain.PersonDocument;
import br.com.nivlabs.cliniv.models.domain.PersonDocumentPK;
import br.com.nivlabs.cliniv.models.dto.AddressDTO;
import br.com.nivlabs.cliniv.models.dto.PatientInfoDTO;
import br.com.nivlabs.cliniv.models.dto.PersonInfoDTO;
import br.com.nivlabs.cliniv.repository.HealthPlanRepository;
import br.com.nivlabs.cliniv.repository.PatientRepository;
import br.com.nivlabs.cliniv.repository.PersonDocumentRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.person.PersonService;
import br.com.nivlabs.cliniv.util.StringUtils;

@Component
public abstract class CreateOrUpdatePatientBusinessHandler implements BaseBusinessHandler {

    @Autowired
    Logger logger;

    @Autowired
    PatientRepository patientRepo;
    @Autowired
    PersonService personService;
    @Autowired
    HealthPlanRepository healthPlanDao;

    @Autowired
    PersonDocumentRepository docRepo;

    @Autowired
    SearchPatientBusinessHandler searchPatientBusiness;

    /**
     * Valida Código SUS do paciente
     * 
     * @param entity
     * @param patient
     */
    @Transactional
    protected void checkCnsCode(PatientInfoDTO entity, Patient patient) {
        if (entity.getCnsNumber() != null && !entity.getCnsNumber().equals(patient.getCnsNumber())) {
            Patient patientByCnsNumber = patientRepo.findByCnsNumber(entity.getCnsNumber()).orElse(null);
            if (patientByCnsNumber != null && !patient.equals(patientByCnsNumber)) {
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Já existe um outro paciente utilizando este código SUS, você não pode utilizar neste cadastro.");
            }
        }
    }

    /**
     * Realiza a checagem da existência do paciente para o CPF informado
     * 
     * @param cpf CPF do paciente / pessoa física
     * @param id Identificador do paciente
     */
    @Transactional
    protected void patientCheckIfExistsByCpf(String cpf, Long id) {
        logger.info("Verificando se já há cadastro de paciente na base de dados :: CPF da busca -> {}", cpf);
        PatientInfoDTO patient = searchPatientBusiness.getByCpf(cpf);
        if (patient != null && patient.getId() != null && !patient.getId().equals(id)) {
            logger.warn("Paciente com o CPF {} já cadastrado.", cpf);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Paciente com o CPF informado já está cadastrado, não é possível realizar um outro cadastro com o mesmo CPF(%s).",
                                  cpf));
        }
    }

    /**
     * Valida documento para atualização
     * 
     * @param request Objeto de transferência com informações do paciente
     * @param patientEntity Objeto de entidade relacional de paciente
     * @param personEntity Objeto de entidade relacional de pessoa física
     */
    @Transactional
    protected void checkDocument(PatientInfoDTO request, Patient patientEntity, Person personEntity) {
        if (request.getDocument() != null && request.getDocument().getType().equals(DocumentType.CPF)
                && !StringUtils.isNullOrEmpty(request.getDocument().getValue())) {
            personEntity.setCpf(request.getDocument().getValue());
            Patient patientByCpf = patientRepo.findByCpf(request.getDocument().getValue()).orElse(null);
            if (patientByCpf != null && !patientEntity.equals(patientByCpf)) {
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Já existe um outro paciente utilizando este CPF, você não pode utilizar neste cadastro.");
            }
        } else {
            personEntity.setCpf(null);
        }
    }

    /**
     * Process o endereço
     * 
     * @param request Objeto de transferência com informações do paciente
     * @param entity Objeto de entidade relacional de pessoa física
     */
    @Transactional
    protected void addressProcess(PatientInfoDTO request, Person entity) {
        logger.info("Verificando endereço");
        if (request.getAddress() != null) {
            AddressDTO address = request.getAddress();
            PersonAddress personAddress = new PersonAddress();
            if (entity.getAddress() != null)
                personAddress = entity.getAddress();
            BeanUtils.copyProperties(address, personAddress);
            personAddress.setPerson(entity);
            entity.setAddress(personAddress);
        }
    }

    /**
     * Processa documentos antes da persistência
     * 
     * @param request Objeto de transferência com informações do paciente
     * @param entity Objeto de entidade relacional de pessoa física
     */
    @Transactional
    protected void documentsProcess(PatientInfoDTO request, Person entity) {
        logger.info("Iniciando processo de checagem de documentos...");
        if (entity != null) {
            logger.info("Removendo documentos anteriores...");
            docRepo.deleteByPerson(new Person(entity.getId()));
            docRepo.flush();
        }
        if (request.getDocuments() != null && request.getDocuments().size() > 0) {
            logger.info("Adicionando novos documentos...");
            request.getDocuments().forEach(document -> {
                PersonDocument convertedDoc = new PersonDocument();
                BeanUtils.copyProperties(document, convertedDoc);
                convertedDoc.setPerson(new Person(entity.getId()));
                convertedDoc.setId(new PersonDocumentPK(entity.getId(), document.getType(), document.getValue()));
                docRepo.saveAndFlush(convertedDoc);
                logger.info("Documento adicionado :: {}", document);
            });
        }
        logger.info("Processamento de documentos finalizado com sucesso!");
    }

    /**
     * Trata o cadastro de plano de saúde do paciente
     * 
     * @param request Objeto de transferência com informações do paciente
     * @param entity Objeto de entidade relacional de paciente
     */
    @Transactional
    protected void handleHealthPlah(PatientInfoDTO request, Patient entity) {
        if (request.getHealthPlan() != null
                && !StringUtils.isNullOrEmpty(request.getHealthPlan().getPatientPlanNumber())) {
            logger.info("Iniciando processo de validação de Plano de Saúde do paciente...");
            if (request.getHealthPlan().getPlanCode() == null) {
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O plano de saúde deve ser informado!");
            }

            HealthPlan planFromDb = healthPlanDao.findByPlanCode(request.getHealthPlan().getPlanCode()).orElseThrow(() -> new HttpException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Plano de saúde não cadastrado"));
            entity.setHealthPlanCode(request.getHealthPlan().getPatientPlanNumber());
            entity.setHealthPlan(planFromDb);
            logger.info("Plano de saúde validado com sucesso :: Identificador :: {}", planFromDb.getId());
        }
    }

    /**
     * Adiciona o tipo de identificação do paciente (Identificado ou Não identificado)
     * 
     * @param newPatientEntity
     */
    @Transactional
    protected void handlePatientType(Patient newPatientEntity) {
        logger.info("Checando tipo de paciente...");
        if (StringUtils.isNullOrEmpty(newPatientEntity.getCnsNumber())
                && StringUtils.isNullOrEmpty(newPatientEntity.getPerson().getMotherName())
                && StringUtils.isNullOrEmpty(newPatientEntity.getPerson().getCpf())) {
            newPatientEntity.setType(PatientType.NOT_IDENTIFIED);
        } else {
            newPatientEntity.setType(PatientType.IDENTIFIED);
        }
        logger.info("Tipo de paciente :: {}", newPatientEntity.getType());
    }

    /**
     * Realiza um parse do objeto de transferência do Paciente para Entidade Paciente
     * 
     * @param request Objeto de transferência de paciente
     * @param entity Entidade relacional de paciente
     */
    protected void parsePropertiesToEntity(PatientInfoDTO request, Patient entity) {
        entity.setAnnotations(request.getAnnotations());
        entity.setCnsNumber(request.getCnsNumber());
        entity.setType(request.getType());
    }

    /**
     * Realiza o parse de informações do objeto de transferência para o objeto de entidade relacional de pessoa física
     * 
     * @param patientInfo Objeto de transferência com informações do paciente
     * @param personInfo Objeto de transferência com informações de pessoa física
     */
    protected void parsePropertiesToPersonInfo(PatientInfoDTO patientInfo, PersonInfoDTO personInfo) {
        logger.info("Iniciando processo de conversão de paciente para pessoa");
        personInfo.setId(patientInfo.getPersonId());
        personInfo.setFullName(patientInfo.getFullName());
        personInfo.setSocialName(patientInfo.getSocialName());
        personInfo.setDocument(patientInfo.getDocument());
        personInfo.setGender(patientInfo.getGender());
        personInfo.setGenderIdentity(patientInfo.getGenderIdentity());
        personInfo.setFatherName(patientInfo.getFatherName());
        personInfo.setMotherName(patientInfo.getMotherName());
        personInfo.setBornDate(patientInfo.getBornDate());
        personInfo.setPrincipalNumber(patientInfo.getPrincipalNumber());
        personInfo.setSecondaryNumber(patientInfo.getSecondaryNumber());
        personInfo.setProfilePhoto(patientInfo.getProfilePhoto());
        personInfo.setEmail(patientInfo.getEmail());
        personInfo.setEthnicGroup(patientInfo.getEthnicGroup());
        personInfo.setBloodType(patientInfo.getBloodType());
        personInfo.setNationality(patientInfo.getNationality());
        personInfo.setAddress(patientInfo.getAddress());
        personInfo.setDocuments(patientInfo.getDocuments());
    }

}
