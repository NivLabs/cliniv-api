package br.com.nivlabs.gp.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.repository.HealthOperatorRepository;

/**
 * 
 * Classe HealthOperatorService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 04 de agosto de 2020
 */
@Service
public class HealthOperatorService implements GenericService {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HealthOperatorRepository healthOperatorRepository;

    /**
     * Busca uma página de operadoras de planos de saúde
     * 
     * @param pageRequest
     * @return Page
     */
    public Page<HealthOperatorDTO> getListOfHealthOperator(HealthOperatorFilters filters, Pageable pageRequest) {
        return healthOperatorRepository.resumedList(filters, pageRequest);
    }

}
