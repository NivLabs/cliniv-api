package br.com.nivlabs.gp.service.healthplan.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.repository.HealthOperatorRepository;
import br.com.nivlabs.gp.repository.HealthPlanRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * Componente base para criação de operações como Criação e Atualização de planos de saúde
 * 
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public abstract class CreateOrUpdateHealthPlanBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected HealthPlanRepository healthPlanRepository;

    @Autowired
    protected HealthOperatorRepository healthOperatorRepository;

    /**
     * Converte Objeto DTO para entidade
     * 
     * @param healthPlanInfo DTO com informações de plano de saúde
     * @return Entidade com informações de plano de saúde
     */
    protected HealthPlan parseDTOInfoToEntity(HealthPlanDTO healthPlanInfo, HealthPlan healthPlanEntity) {
        logger.info("Iniciando processo de conversão de DTO para entidade...");
        healthPlanEntity.setId(healthPlanInfo.getId());
        healthPlanEntity.setPlanCode(healthPlanInfo.getPlanCode());

        healthPlanEntity.setId(healthPlanInfo.getId());
        healthPlanEntity.setPlanCode(healthPlanInfo.getPlanCode());
        healthPlanEntity.setCommercialName(healthPlanInfo.getCommercialName());
        healthPlanEntity.setSegmentation(healthPlanInfo.getSegmentation());
        healthPlanEntity.setContractType(healthPlanInfo.getContractType());
        healthPlanEntity.setAbrangency(healthPlanInfo.getAbrangency());
        healthPlanEntity.setType(healthPlanInfo.getType());

        logger.info("Entidade convertida :: {}", healthPlanEntity);

        return healthPlanEntity;
    }

}
