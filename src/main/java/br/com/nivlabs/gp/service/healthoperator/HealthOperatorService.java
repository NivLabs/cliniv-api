package br.com.nivlabs.gp.service.healthoperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.healthoperator.business.CreateHealthOperatorBusinessHandler;
import br.com.nivlabs.gp.service.healthoperator.business.SearchHealthOperatorBusinessHandler;
import br.com.nivlabs.gp.service.healthoperator.business.UpdateHealthOperatorBusinessHandler;

/**
 * 
 * Classe HealthOperatorService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 04 de agosto de 2020
 */
@Service
public class HealthOperatorService implements BaseService {

    @Autowired
    private SearchHealthOperatorBusinessHandler searchHealthOperatorBusinessHandler;
    @Autowired
    private CreateHealthOperatorBusinessHandler createHealthOperatorBusinessHandler;
    @Autowired
    private UpdateHealthOperatorBusinessHandler updateHealthOperatorBusinessHandler;

    /**
     * Busca uma página de operadoras de planos de saúde
     * 
     * @param filters Filtros de busca
     * @param pageRequest Configurações de paginação
     * @return Página de operadoras de planos de saúde
     */
    public Page<HealthOperatorDTO> getPage(HealthOperatorFilters filters, Pageable pageRequest) {
        return searchHealthOperatorBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * Busca os detalhes de uma operadora de plano de saúde à partir do identificador único
     * 
     * @param id Identificador único da operadora de saúde
     * @return Informações detalhadas da operadora de saúde
     */
    public HealthOperatorInfoDTO findByHealthOperatorId(Long id) {
        return searchHealthOperatorBusinessHandler.byId(id);
    }

    /**
     * Cria um novo cadastro de operadora de saúde
     * 
     * @param request Objeto de requisição (DTO) de Operadora de saúde
     * @return Objeto cadastrado na aplicação
     */
    public HealthOperatorInfoDTO create(HealthOperatorInfoDTO request) {
        return createHealthOperatorBusinessHandler.create(request);
    }

    /**
     * Atualizando informações de operadora de saúde
     * 
     * @param id Identificador único interno da operadora de saúde
     * @param request Objeto da requisição (DTO) de Operadora de saúde
     * @return Objeto atualizado com as novas informações
     */
    public HealthOperatorInfoDTO update(Long id, HealthOperatorInfoDTO request) {
        request.setId(id);
        return updateHealthOperatorBusinessHandler.update(request);
    }

}
