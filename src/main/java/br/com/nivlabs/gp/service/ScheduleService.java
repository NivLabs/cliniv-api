package br.com.nivlabs.gp.service;

import java.time.LocalDate;
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
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.Person_;
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

    public ScheduleInfoDTO create(ScheduleInfoDTO request) {
        return null;
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
