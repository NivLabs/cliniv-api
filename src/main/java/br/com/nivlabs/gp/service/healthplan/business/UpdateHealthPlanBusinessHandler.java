package br.com.nivlabs.gp.service.healthplan.business;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;

/**
 * 
 * Componente específico para atualização de informações do plano de saúde
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public class UpdateHealthPlanBusinessHandler extends CreateOrUpdateHealthPlanBusinessHandler {

    /**
     * Atualiza os dados de um plano de saúde já cadastrado
     * 
     * @param healthPlanInfo Objeto da requisição (Plano de Saúde) para atualização
     * @return Objeto atualizado
     */
    @Transactional
    public HealthPlanDTO update(HealthPlanDTO healthPlanInfo) {
        logger.info("Iniciando processo de atualização de cadastro de Plano de Saúde...");

        HealthPlan healthPlanEntity = healthPlanRepository.findById(healthPlanInfo.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o id %s não encontrada.", healthPlanInfo.getId())));

        parseDTOInfoToEntity(healthPlanInfo, healthPlanEntity);

        healthPlanEntity.setHealthOperator(healthOperatorRepository.findByAnsCode(healthPlanInfo.getOperatorCode())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "A Operadora informada no cadastro do Plano não está cadastrada no sistema, favor realizar o cadastro da mesma.")));

        healthPlanRepository.save(healthPlanEntity);

        return healthPlanInfo;
    }

}
