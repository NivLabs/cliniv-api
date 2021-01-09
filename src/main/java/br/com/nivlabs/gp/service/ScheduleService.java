package br.com.nivlabs.gp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.Schedule;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.gp.repository.ScheduleRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Classe ScheduleService.java
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ScheduleService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScheduleRepository principalRepo;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private PatientService patientService;

    /**
     * Realiza uma busca filtrada de agendamentos baseado na data
     * 
     * @param filters Filtros da requisição (Query Param)
     * @return Lista filtrada de Agendamentos
     */
    public List<ScheduleInfoDTO> findByFilters(ScheduleFilters filters, Pageable pageRequest) {
        logger.info("Iniciando a busca filtrada por informações da agenda");
        if (filters.getSelectedDate() == null) {
            filters.setSelectedDate(LocalDate.now());
        }
        return principalRepo.resumedList(filters, pageRequest).getContent();
    }

    /**
     * Cria um agendamento
     * 
     * @param request Informações de um agendamento
     * @return Informações de um agendamento atualizado pós persistência
     */
    public ScheduleInfoDTO create(ScheduleInfoDTO request) {
        logger.info("Iniciando processo de criação de agendamento");
        validateRequest(null, request);
        return persist(request);
    }

    /**
     * Atualiza informações de um agendamento
     * 
     * @param id Identificador único do agendamento
     * @param request Informações de uma atualização de agendamento
     * @return Informações de um agendamento pós atualização
     */
    public ScheduleInfoDTO update(Long id, ScheduleInfoDTO request) {
        logger.info("Iniciando processo de atualização de agendamento");
        validateRequest(id, request);
        return persist(request);
    }

    /**
     * Cria ou atualiza agendamento
     * 
     * @param request
     * @return
     */
    private ScheduleInfoDTO persist(ScheduleInfoDTO request) {
        Schedule entity = new Schedule();
        BeanUtils.copyProperties(request, entity);
        entity.setPatient(new Patient(request.getPatient().getId()));
        entity.setProfessional(new Responsible(request.getProfessional().getId()));
        principalRepo.saveAndFlush(entity);
        request.setId(entity.getId());
        return request;
    }

    /**
     * Valida se a requisição é válida
     * 
     * @param request
     */
    private void validateRequest(Long id, ScheduleInfoDTO request) {
        if (id != null) {
            principalRepo.findById(id)
                    .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));
        }
        request.setId(id);
        checkPatient(request.getPatient());
        checkProfessional(request.getProfessional());
        logger.info("Verificando data de criação do agendamento...");
        if (request.getCreatedAt() == null) {
            request.setCreatedAt(LocalDateTime.now());
            logger.warn("Data não encontrada... Inicializando com a data/hora atual do servidor :: {}", request.getCreatedAt());
        }
        if (request.getSchedulingDateAndTime() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe a data/hora à ser agendada");
        }

    }

    /**
     * Verifica se o profissional informado já está cadastrado
     * 
     * @param professional
     */
    private void checkProfessional(ResponsibleInfoDTO professional) {
        logger.info("Verificando se o profissional já está cadastrado...");
        if (professional.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O identificador do profissional deve ser informado para o agendamento");
        }
        ResponsibleInfoDTO professionalFromDb = responsibleService.findById(professional.getId());
        logger.info("Profissional encontrado :: Cod: {} | Nome: {}", professionalFromDb.getId(), professionalFromDb.getFullName());

    }

    /**
     * Verifica se o paciente informado já está cadastrado
     * 
     * @param patient
     */
    private void checkPatient(PatientInfoDTO patient) {
        logger.info("Verificando se o paciente já está cadastrado...");
        if (patient.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O identificador do paciente deve ser informado para o agendamento");
        }
        PatientInfoDTO patientFromDb = patientService.findByPatientId(patient.getId());
        logger.info("Paciente encontrado :: Cod: {} | Nome: {}", patientFromDb.getId(), patientFromDb.getFullName());
    }

    /**
     * Busca agendamento por identificador único
     * 
     * @param id Identificador único do agendamento
     * @return Informações detalhadas do agendamento
     */
    public ScheduleInfoDTO findById(Long id) {
        Schedule objFromDb = principalRepo.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Agendamento não encontrado!"));

        logger.info("Paciente {} encontrado com sucesso!", objFromDb.getPatient().getPerson().getFullName());
        ScheduleInfoDTO response = new ScheduleInfoDTO();

        logger.info("Iniciando conversão de entidade Agendamento para resposta...");
        BeanUtils.copyProperties(objFromDb, response);

        logger.info("Iniciando conversão de entidade Paciente para resposta...");
        response.setPatient(new PatientInfoDTO());
        BeanUtils.copyProperties(objFromDb.getPatient().getPerson(), response.getPatient(), Person_.ID);
        BeanUtils.copyProperties(objFromDb.getPatient(), response.getPatient());
        handleDocument(objFromDb.getPatient().getPerson(), response.getPatient());

        logger.info("Iniciando conversão de entidade Responsável para resposta...");
        response.setProfessional(new ResponsibleInfoDTO());
        BeanUtils.copyProperties(objFromDb.getProfessional().getPerson(), response.getProfessional(), Person_.ID);
        BeanUtils.copyProperties(objFromDb.getProfessional(), response.getProfessional());
        handleDocument(objFromDb.getProfessional().getPerson(), response.getProfessional());

        logger.info("Conversões realizadas com sucesso, enviando resposta de volta para o client");
        return response;
    }

    /**
     * Trata informações de documento no paciente
     * 
     * @param source
     * @param target
     */
    private void handleDocument(Person source, PatientInfoDTO target) {
        if (StringUtils.isNullOrEmpty(source.getCpf())) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O cadastro do paciente está sem CPF informado, favor completar o cadastro antes de realizar um agendamento");
        }
        DocumentDTO document = new DocumentDTO(DocumentType.CPF, source.getCpf());
        target.setDocument(document);
    }

    /**
     * Trata informações de documento no responsável (Profissional)
     * 
     * @param source
     * @param target
     */
    private void handleDocument(Person source, ResponsibleInfoDTO target) {
        if (StringUtils.isNullOrEmpty(source.getCpf())) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O cadastro do paciente está sem CPF informado, favor completar o cadastro antes de realizar um agendamento");
        }
        DocumentDTO document = new DocumentDTO(DocumentType.CPF, source.getCpf());
        target.setDocument(document);
    }
}
