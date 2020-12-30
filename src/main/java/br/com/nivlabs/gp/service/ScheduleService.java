package br.com.nivlabs.gp.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.gp.repository.ScheduleRepository;

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

}
