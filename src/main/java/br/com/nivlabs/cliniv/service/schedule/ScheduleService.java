package br.com.nivlabs.cliniv.service.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.controller.filters.ScheduleFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.domain.Schedule;
import br.com.nivlabs.cliniv.models.dto.PatientInfoDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.models.dto.ScheduleDTO;
import br.com.nivlabs.cliniv.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.cliniv.repository.ScheduleRepository;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.patient.PatientService;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;

/**
 * Classe ScheduleService.java
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ScheduleService implements BaseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScheduleRepository principalRepo;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private PatientService patientService;

    @Autowired
    private SearchScheduleBusinessHandler searchScheduleBusinessHandler;

    /**
     * Realiza uma busca filtrada de agendamentos baseado na data
     * 
     * @param filters Filtros da requisição (Query Param)
     * @return Lista filtrada de Agendamentos
     */
    public List<ScheduleDTO> findByFilters(ScheduleFilters filters) {
        return searchScheduleBusinessHandler.getPage(filters).getContent();
    }

    /**
     * Busca agendamento por identificador único
     * 
     * @param id Identificador único do agendamento
     * @return Informações detalhadas do agendamento
     */
    public ScheduleInfoDTO findById(Long id) {
        return searchScheduleBusinessHandler.byId(id);
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
            Schedule schedule = principalRepo.findById(id)
                    .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));
            logger.warn("Agendamento localizado, prosseguindo com as validações :: Cod: {} | Paciente: {} | Profissional {}",
                        schedule.getId(), schedule.getPatient().getPerson().getFullName(),
                        schedule.getProfessional().getPerson().getFullName());
        }
        request.setId(id);
        checkPatient(request);
        checkProfessional(request);
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
    private void checkProfessional(ScheduleInfoDTO request) {
        ResponsibleInfoDTO professional = request.getProfessional();
        logger.info("Verificando se o profissional já está cadastrado...");
        if (professional == null || professional.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O identificador do profissional deve ser informado para o agendamento");
        }
        professional = responsibleService.findById(professional.getId());
        logger.info("Profissional encontrado :: Cod: {} | Nome: {}", professional.getId(), professional.getFullName());
        request.setProfessional(professional);

    }

    /**
     * Verifica se o paciente informado já está cadastrado
     * 
     * @param patient
     */
    private void checkPatient(ScheduleInfoDTO request) {
        PatientInfoDTO patient = request.getPatient();
        logger.info("Verificando se o paciente já está cadastrado...");
        if (patient == null || patient.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O identificador do paciente deve ser informado para o agendamento");
        }
        patient = patientService.findByPatientId(patient.getId());
        logger.info("Paciente encontrado :: Cod: {} | Nome: {}", patient.getId(), patient.getFullName());
        request.setPatient(patient);
    }
}
