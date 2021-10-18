package br.com.nivlabs.gp.service.schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.PersonDocument;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.Schedule;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.ProfessionalIdentityDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.ScheduleDTO;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.gp.repository.ScheduleRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Componente específico para busca de agendamentos
 * 
 *
 * @author viniciosarodrigues
 * @since 10-10-2021
 *
 */
@Component
public class SearchScheduleBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected ScheduleRepository scheduleRepository;

    /**
     * Consulta de agendamento paginada
     * 
     * @param filters Filtros de pesquisa
     * @param pageRequest Configurações de paginação
     * @return Lista paginada de agendamentos
     */
    public Page<ScheduleDTO> getPage(ScheduleFilters filters, Pageable pageRequest) {
        logger.info("Iniciando a busca filtrada por informações da agenda");
        if (filters.getSelectedDate() == null) {
            filters.setSelectedDate(LocalDate.now());
        }
        filters.setSize(100);
        return scheduleRepository.resumedList(filters, pageRequest);
    }

    /**
     * Busca agendamento por identificador único
     * 
     * @param id Identificador único do agendamento
     * @return Informações detalhadas do agendamento
     */
    @Transactional
    public ScheduleInfoDTO byId(Long id) {
        Schedule scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Agendamento não encontrado!"));

        logger.info("Paciente {} encontrado com sucesso!", scheduleEntity.getPatient().getPerson().getFullName());

        ScheduleInfoDTO response = new ScheduleInfoDTO();

        parseScheduleEntityToDTO(scheduleEntity, response);

        return response;
    }

    /**
     * Transfere informações da entidade de agendamento
     * 
     * @param scheduleEntity Entidade relacional de agendamento
     * @param scheduleInfo Objeto de transferência de agendamento
     */
    @Transactional
    private void parseScheduleEntityToDTO(Schedule scheduleEntity, ScheduleInfoDTO scheduleInfo) {
        logger.info("Iniciando conversão de entidade Agendamento para resposta...");
        scheduleInfo.setId(scheduleEntity.getId());
        scheduleInfo.setAnnotation(scheduleEntity.getAnnotation());
        scheduleInfo.setCreatedAt(scheduleEntity.getCreatedAt());
        scheduleInfo.setSchedulingDateAndTime(scheduleEntity.getSchedulingDateAndTime());
        scheduleInfo.setStatus(scheduleEntity.getStatus());

        scheduleInfo.setPatient(new PatientInfoDTO());
        parsePatientEntityToDTO(scheduleEntity.getPatient(), scheduleInfo.getPatient());

        handleDocument(scheduleEntity.getPatient().getPerson(), scheduleInfo.getPatient());

        scheduleInfo.setProfessional(new ResponsibleInfoDTO());
        parseProfessionalEntityToDTO(scheduleEntity.getProfessional(), scheduleInfo.getProfessional());
        handleDocument(scheduleEntity.getProfessional().getPerson(), scheduleInfo.getProfessional());

    }

    /**
     * Transfere informações de uma entidade relacional para um objeto de transferência de profissional
     * 
     * @param professionalEntity Entidade relacional de profissional
     * @param professionalInfo Objeto de transferência de profissional
     */
    private void parseProfessionalEntityToDTO(Responsible professionalEntity, ResponsibleInfoDTO professionalInfo) {
        logger.info("Iniciando conversão de entidade Responsável para resposta...");

        professionalInfo.setId(professionalEntity.getId());
        professionalInfo.setCreatedAt(professionalEntity.getCreatedAt());

        professionalInfo.setProfessionalIdentity(new ProfessionalIdentityDTO(professionalEntity.getInitialsIdentity(),
                professionalEntity.getProfessionalIdentity()));
        professionalInfo.setCreatedAt(professionalInfo.getCreatedAt());

        parsePersonEntityToProfessionalInfo(professionalEntity.getPerson(), professionalInfo);

    }

    /**
     * Transfere informações da entidade pessoa para DTO de profissional
     * 
     * @param person Entidade relacional de pessoa
     * @param professionalInfo DTO de profissional
     */
    @Transactional
    private void parsePersonEntityToProfessionalInfo(Person person, ResponsibleInfoDTO professionalInfo) {
        professionalInfo.setFullName(person.getFullName());
        professionalInfo.setSocialName(person.getSocialName());
        professionalInfo.setBornDate(person.getBornDate());
        professionalInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        professionalInfo.setDocuments(convertDocuments(person.getDocuments()));
        professionalInfo.setGender(person.getGender());
        professionalInfo.setGenderIdentity(person.getGenderIdentity());
        professionalInfo.setFatherName(person.getFatherName());
        professionalInfo.setMotherName(person.getMotherName());
        professionalInfo.setPrincipalNumber(person.getPrincipalNumber());
        professionalInfo.setSecondaryNumber(person.getSecondaryNumber());
        professionalInfo.setEmail(person.getEmail());
        professionalInfo.setEthnicGroup(person.getEthnicGroup());
        professionalInfo.setBloodType(person.getBloodType());
        professionalInfo.setNationality(person.getNationality());
        professionalInfo.setProfilePhoto(person.getProfilePhoto());

    }

    /**
     * Transfere informações de uma entidade relacional para um objeto de transferência do paciente
     * 
     * @param patientEntity Entidade relacional de paciente
     * @param patientInfo Objeto de transferência de paciente
     */
    @Transactional
    private void parsePatientEntityToDTO(Patient patientEntity, PatientInfoDTO patientInfo) {
        logger.info("Iniciando conversão de entidade Paciente para resposta...");

        patientInfo.setId(patientEntity.getId());
        patientInfo.setCnsNumber(patientEntity.getCnsNumber());

        if (patientEntity.getHealthPlan() != null) {
            var healthPlanEntity = patientEntity.getHealthPlan();
            HealthPlanDTO healthPlanInfo = new HealthPlanDTO();
            healthPlanInfo.setId(healthPlanEntity.getId());
            healthPlanInfo.setPlanCode(healthPlanEntity.getPlanCode());
            healthPlanInfo.setCommercialName(healthPlanEntity.getCommercialName());
            healthPlanInfo.setAbrangency(healthPlanEntity.getAbrangency());
            healthPlanInfo.setContractType(healthPlanEntity.getContractType());
            healthPlanInfo.setOperatorCode(healthPlanEntity.getHealthOperator().getAnsCode());
            healthPlanInfo.setOperatorName(healthPlanEntity.getHealthOperator().getCompanyName());
            healthPlanInfo.setSegmentation(healthPlanEntity.getSegmentation());
            patientInfo.setHealthPlan(healthPlanInfo);
            logger.info("Plano de saúde do paciente :: {}", healthPlanInfo);
        }

        patientEntity.getAllergies().forEach(allergy -> patientInfo.getAllergies().add(allergy.getDescription()));

        parsePersonEntityToPatientInfo(patientEntity.getPerson(), patientInfo);

    }

    /**
     * Converte entidade do modelo relacional em objeto de transferência
     * 
     * @param patient Entidade do modelo relacional
     * @return Objeto de transferência
     */
    @Transactional
    private PatientInfoDTO parsePersonEntityToPatientInfo(Person person, PatientInfoDTO patientInfo) {
        logger.info("Iniciando processo de conversão de dados de entidade para objeto de transferência :: Patient -> PatientInfoDTO");

        patientInfo.setPersonId(person.getId());
        patientInfo.setFullName(person.getFullName());
        patientInfo.setSocialName(person.getSocialName());
        patientInfo.setBornDate(person.getBornDate());
        patientInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        patientInfo.setDocuments(convertDocuments(person.getDocuments()));
        patientInfo.setGender(person.getGender());
        patientInfo.setGenderIdentity(person.getGenderIdentity());
        patientInfo.setFatherName(person.getFatherName());
        patientInfo.setMotherName(person.getMotherName());
        patientInfo.setPrincipalNumber(person.getPrincipalNumber());
        patientInfo.setSecondaryNumber(person.getSecondaryNumber());
        patientInfo.setEmail(person.getEmail());

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            address.setAddressNumber(person.getAddress().getAddressNumber());
            address.setCity(person.getAddress().getCity());
            address.setComplement(person.getAddress().getComplement());
            address.setNeighborhood(person.getAddress().getNeighborhood());
            address.setPostalCode(person.getAddress().getPostalCode());
            address.setState(person.getAddress().getState());
            address.setStreet(person.getAddress().getStreet());
            patientInfo.setAddress(address);
        }

        patientInfo.setProfilePhoto(person.getProfilePhoto());

        patientInfo.setEthnicGroup(person.getEthnicGroup());
        patientInfo.setBloodType(person.getBloodType());
        patientInfo.setNationality(person.getNationality());

        return patientInfo;
    }

    /**
     * Converte documentos de domínio para documentos de transferência
     * 
     * @param documents Lista de documentos à serem convertidos
     * @return Lista de documentos convertidos
     */
    @Transactional
    private List<DocumentDTO> convertDocuments(List<PersonDocument> documents) {
        List<DocumentDTO> convertedDocuments = new ArrayList<>();

        documents.forEach(doc -> {
            DocumentDTO convertedDoc = new DocumentDTO();
            BeanUtils.copyProperties(doc, convertedDoc);
            convertedDoc.setPersonId(doc.getId().getPersonId());
            convertedDoc.setType(doc.getId().getType());
            convertedDoc.setValue(doc.getId().getValue());
            convertedDocuments.add(convertedDoc);
        });

        return convertedDocuments;
    }

    /**
     * Trata informações de documento no paciente
     * 
     * @param personEntity
     * @param personInfo
     */
    @Transactional
    private void handleDocument(Person personEntity, PatientInfoDTO personInfo) {
        if (StringUtils.isNullOrEmpty(personEntity.getCpf())) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O cadastro do paciente está sem CPF informado, favor completar o cadastro antes de realizar um agendamento");
        }
        DocumentDTO document = new DocumentDTO(null, DocumentType.CPF, personEntity.getCpf(), null, null, null, null);
        personInfo.setDocument(document);
    }

    /**
     * Trata informações de documento no responsável (Profissional)
     * 
     * @param source
     * @param target
     */
    @Transactional
    private void handleDocument(Person source, ResponsibleInfoDTO target) {
        if (StringUtils.isNullOrEmpty(source.getCpf())) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O cadastro do paciente está sem CPF informado, favor completar o cadastro antes de realizar um agendamento");
        }
        DocumentDTO document = new DocumentDTO(null, DocumentType.CPF, source.getCpf(), null, null, null, null);
        target.setDocument(document);
    }

}