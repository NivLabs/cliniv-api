package br.com.nivlabs.gp.service.healthoperator.business;

import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;

/**
 * 
 * Componente específico para criação de cadastro de operadoras de saúde
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public class CreateHealthOperatorBusinessHandler extends CreateOrUpdateHealthOperatorBusinessHandler {

    public HealthOperatorInfoDTO create(HealthOperatorInfoDTO healthOperatorInfo) {
        logger.info("Iniciando processo de criação de cadastro de Operadora/Convênio de Saúde..");
        healthOperatorInfo.setId(null);

        HealthOperator healthOperator = convertRequestToEntity(healthOperatorInfo);
        logger.info("Salvando criação de cadastro no banco");
        healthOperatorRepository.save(healthOperator);
        healthOperatorInfo.setId(healthOperator.getId());

        logger.info("Nova operadora cadastrada com sucesso, devolvendo resposta atualizada :: {}", healthOperatorInfo);
        return healthOperatorInfo;
    }

}
