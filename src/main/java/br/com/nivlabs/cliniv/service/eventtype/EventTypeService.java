package br.com.nivlabs.cliniv.service.eventtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.domain.EventType;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.eventtype.business.SearchEventTypeBusinessHandler;

/**
 * 
 * Camada de serviços criada para Tipos de eventos
 *
 * @author viniciosarodrigues
 * @since 20-09-2021
 *
 */
@Service
public class EventTypeService implements BaseService {

    @Autowired
    private SearchEventTypeBusinessHandler searchEventTypeBusinessHandler;

    /**
     * Busca uma lista paginada de Tipos de eventos de atendimentos
     * 
     * @param pageRequest Configurações de paginação
     * @return Lista paginada de tipos de eventos
     */
    public Page<EventType> searchEntityPage(Pageable pageRequest) {
        return searchEventTypeBusinessHandler.getPage(pageRequest);
    }

}
