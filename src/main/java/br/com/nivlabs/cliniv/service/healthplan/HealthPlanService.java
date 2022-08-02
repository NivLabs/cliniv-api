package br.com.nivlabs.cliniv.service.healthplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.HealthPlanDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.healthplan.business.CreateHealthPlanBusinessHandler;
import br.com.nivlabs.cliniv.service.healthplan.business.DeleteHealthPlanBusinessHandler;
import br.com.nivlabs.cliniv.service.healthplan.business.SearchHealthPlanBusinessHandler;
import br.com.nivlabs.cliniv.service.healthplan.business.UpdateHealthPlanBusinessHandler;

/**
 * 
 * Camada de serviços para manipulação de Planos de Saúde
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Service
public class HealthPlanService implements BaseService {

    @Autowired
    private SearchHealthPlanBusinessHandler searchHealthPlanBusinessHandler;
    @Autowired
    private UpdateHealthPlanBusinessHandler updateHealthPlanBusinessHandler;
    @Autowired
    private CreateHealthPlanBusinessHandler createHealthPlanBusinessHandler;
    @Autowired
    private DeleteHealthPlanBusinessHandler deleteHealthPlanBusinessHandler;

    /**
     * Busca informações do plano de saúde pelo identificador único do plano
     * 
     * @param id Identificador único do plano de saúde
     * @return Informações do plano de saúde
     */
    public HealthPlanDTO findHealthPlanById(Long id) {
        return searchHealthPlanBusinessHandler.byId(id);
    }

    /**
     * Busca informações do plano de saúde pelo código ANS
     * 
     * @param ansCode Código do plano na ANS
     * @return Informações do plano de saúde
     */
    public HealthPlanDTO findHealthPlanByAnsCode(Long ansCode) {
        return searchHealthPlanBusinessHandler.byANSCode(ansCode);
    }

    /**
     * Atualiza os dados de um plano de saúde já cadastrado
     * 
     * @param request Objeto da requisição (Plano de Saúde) para atualização
     * @return Objeto atualizado
     */
    public HealthPlanDTO update(Long id, HealthPlanDTO request) {
        request.setId(id);
        return updateHealthPlanBusinessHandler.update(request);
    }

    /**
     * Cria um novo cadastro de plano de saúde
     * 
     * @param healthPlanInfo Informações do plano de saúde para cadastro
     * @return Informações do plano de saúde cadastrado
     */
    public HealthPlanDTO create(HealthPlanDTO request) {
        return createHealthPlanBusinessHandler.create(request);
    }

    /**
     * Deleta um plano de saúde da aplicação
     * 
     * @param id Identificador único interno do plano de saúde
     */
    public void delete(Long id) {
        deleteHealthPlanBusinessHandler.byId(id);
    }

}
