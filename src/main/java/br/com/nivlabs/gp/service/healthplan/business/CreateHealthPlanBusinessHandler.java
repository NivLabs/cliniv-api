package br.com.nivlabs.gp.service.healthplan.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;

/**
 * 
 * Componente específico para criação de planos de saúde
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public class CreateHealthPlanBusinessHandler extends CreateOrUpdateHealthPlanBusinessHandler {

    /**
     * Cria um novo cadastro de plano de saúde
     * 
     * @param healthPlanInfo Informações do plano de saúde para cadastro
     * @return Informações do plano de saúde cadastrado
     */
    public HealthPlanDTO create(HealthPlanDTO healthPlanInfo) {
        logger.info("Iniciando processo de criação de cadastro de Plano de Saúde...");
        healthPlanInfo.setId(null);
        HealthOperator healthOperator = healthOperatorRepository.findByAnsCode(healthPlanInfo.getOperatorCode())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "A Operadora informada no cadastro do Plano não está cadastrada no sistema, favor realizar o cadastro da mesma."));

        if (healthPlanRepository.findByPlanCode(healthPlanInfo.getPlanCode()).isPresent()) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Este código de Plano de Saúde já está em uso por outro plano");
        }

        HealthPlan healthPlanEntity = new HealthPlan();
        parseDTOInfoToEntity(healthPlanInfo, healthPlanEntity);

        healthPlanEntity.setHealthOperator(healthOperator);

        healthPlanRepository.save(healthPlanEntity);

        healthPlanInfo.setId(healthPlanEntity.getId());

        logger.info("Plano de saúde cadastrado com sucesso :: ID: {} | Nome: {} | Id Operadora: {} | Nome Operadora: {}",
                    healthPlanInfo.getId(),
                    healthPlanInfo.getCommercialName(), healthOperator.getId(), healthOperator.getCompanyName());

        return healthPlanInfo;
    }

}
