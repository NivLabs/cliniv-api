package br.com.nivlabs.cliniv.service.eventtype.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.EventType;
import br.com.nivlabs.cliniv.repository.EventTypeRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente base para pesquisas de tipos de eventos *
 *
 * @author viniciosarodrigues
 * @since 20-09-2021
 *
 */
@Component
public class SearchEventTypeBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private EventTypeRepository dao;

    public Page<EventType> getPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

}
