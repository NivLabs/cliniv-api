package br.com.ft.gdp.repository.custom.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.PatientFilters;
import br.com.ft.gdp.models.dto.PatientDTO;

/**
 * Interface de repositório customizado para Pacientes
 * 
 * @author viniciosarodrigues
 *
 */
public interface PatientRespositoryCustom {

    /**
     * Realiza a busca paginada reduzida de pacientes
     * 
     * @param filtes
     * @param pageSettings
     * @return
     */
    public Page<PatientDTO> resumedList(PatientFilters filters, Pageable pageSettings);
}
