package br.com.nivlabs.cliniv.service.alergy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.controller.filters.AllergyFilters;
import br.com.nivlabs.cliniv.models.dto.AllergyDTO;
import br.com.nivlabs.cliniv.repository.AllergyRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

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

    public Page<AllergyDTO> getPage(AllergyFilters filters) {
        return allergyRepository.resumedList(filters);
    }
}
