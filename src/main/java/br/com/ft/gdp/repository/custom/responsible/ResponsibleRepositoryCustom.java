package br.com.ft.gdp.repository.custom.responsible;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.ResponsibleFilters;
import br.com.ft.gdp.models.dto.ResponsibleDTO;

/**
 * Representa uma interface para um repositório customizado de Profissionais e responsáveis
 * 
 * @author viniciosarodrigues
 *
 */
public interface ResponsibleRepositoryCustom {

    /**
     * Busca uma lista paginada de Profissionais/Responsáveis
     * 
     * @param filtes
     * @param pageSettings
     * @return
     */
    public Page<ResponsibleDTO> resumedList(ResponsibleFilters filtes, Pageable pageSettings);
}
