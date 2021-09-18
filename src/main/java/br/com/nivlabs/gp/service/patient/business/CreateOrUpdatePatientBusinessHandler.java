package br.com.nivlabs.gp.service.patient.business;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.enums.PatientType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.PersonAddress;
import br.com.nivlabs.gp.models.domain.PersonDocument;
import br.com.nivlabs.gp.models.domain.PersonDocumentPK;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.repository.HealthPlanRepository;
import br.com.nivlabs.gp.repository.PatientRepository;
import br.com.nivlabs.gp.repository.PersonDocumentRepository;
import br.com.nivlabs.gp.repository.PersonRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.StringUtils;

@Component
public abstract class CreateOrUpdatePatientBusinessHandler implements BaseBusinessHandler {

    @Autowired
    Logger logger;

    @Autowired
    PatientRepository patientRepo;
    @Autowired
    PersonRepository personRepo;
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
    protected void checkSusCode(PatientInfoDTO entity, Patient patient) {
        if (entity.getCnsNumber() != null && !entity.getCnsNumber().equals(patient.getCnsNumber())) {
            Patient patientBySusNumber = patientRepo.findByCnsNumber(entity.getCnsNumber()).orElse(null);
            if (patientBySusNumber != null && !patient.equals(patientBySusNumber)) {
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
     * Realiza o parse de informações do objeto de transferência para o objeto de entidade relacional de pessoa física
     * 
     * @param request Objeto de transferência com informações do paciente
     * @param entity Objeto de entidade relacional de pessoa física
     */
    protected void parsePropertiesToEntity(PatientInfoDTO request, Person entity) {
        entity.setFullName(request.getFullName());
        entity.setSocialName(request.getSocialName());
        entity.setCpf(request.getDocument().getValue());
        entity.setGender(request.getGender());
        entity.setGenderIdentity(request.getGenderIdentity());
        entity.setFatherName(request.getFatherName());
        entity.setMotherName(request.getMotherName());
        entity.setBornDate(request.getBornDate());
        entity.setPrincipalNumber(request.getPrincipalNumber());
        entity.setSecondaryNumber(request.getSecondaryNumber());
        entity.setProfilePhoto(request.getProfilePhoto());
        entity.setEmail(request.getEmail());
        entity.setEthnicGroup(request.getEthnicGroup());
        entity.setBloodType(request.getBloodType());
        entity.setNationality(request.getNationality());
    }

}
