package br.com.nivlabs.gp.service.healthoperator.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.gp.repository.HealthOperatorRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * Componente abstrato para uso em Atualização e Criação de operadora de planos de saúde
 * 
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public abstract class CreateOrUpdateHealthOperatorBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected HealthOperatorRepository healthOperatorRepository;

    /**
     * Converte DTO em Entidade relacional
     * 
     * @param healthOperatorInfo DTO que representa informações da operadora de saúde
     * @return Entidade relacional que representa a operadora de saúde
     */
    protected HealthOperator convertRequestToEntity(HealthOperatorInfoDTO healthOperatorInfo) {
        logger.info("Iniciando conversão de DTO para entidade");
        HealthOperator healthOperator = new HealthOperator();
        healthOperator.setId(healthOperatorInfo.getId());
        healthOperator.setAnsCode(healthOperatorInfo.getAnsCode());
        if (healthOperatorInfo.getDocument() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe o CNPJ da operadora!");
        }
        healthOperator.setCnpj(healthOperatorInfo.getDocument().getValue());
        healthOperator.setCompanyName(healthOperatorInfo.getCompanyName());
        healthOperator.setFantasyName(healthOperatorInfo.getFantasyName());
        healthOperator.setModality(healthOperatorInfo.getModality());

        logger.info("Entidade convertida :: {}", healthOperator);

        return healthOperator;
    }

}
