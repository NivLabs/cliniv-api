package br.com.nivlabs.gp.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;

@Service
public class ScheduleService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Realiza uma busca filtrada de agendamentos baseado na data
     * 
     * @param filters Filtros da requisição (Query Param)
     * @return Lista filtrada de Agendamentos
     */
    public List<ScheduleInfoDTO> findByFilters(ScheduleFilters filters) {
        if (filters.getSelectedDate() == null) {
            filters.setSelectedDate(LocalDate.now());
        }
        return Arrays.asList();
    }

}
