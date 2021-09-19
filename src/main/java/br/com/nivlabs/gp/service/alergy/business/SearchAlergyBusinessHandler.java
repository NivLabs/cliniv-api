package br.com.nivlabs.gp.service.alergy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.AllergyFilters;
import br.com.nivlabs.gp.models.dto.AllergyDTO;
import br.com.nivlabs.gp.repository.AllergyRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Classe de negócio para pesquisa de alergias
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
@Component
public class SearchAlergyBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private AllergyRepository allergyRepository;

    public Page<AllergyDTO> getPage(AllergyFilters filters, Pageable pageSettings) {
        return allergyRepository.resumedList(filters, pageSettings);
    }
}
