package br.com.nivlabs.cliniv.service.healthplan.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.HealthPlan;
import br.com.nivlabs.cliniv.repository.HealthPlanRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para exclusão de Plano de Saúde do sistema
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public class DeleteHealthPlanBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected HealthPlanRepository healthPlabRepository;

    /**
     * Remove um plano de saúde por Identificador único da base de dados
     * 
     * @param id Identificador único do plano de saúde
     */
    public void byId(Long id) {
        logger.info("Iniciando busca de plano de saúde para exclusão...");
        HealthPlan healthPlanEntity = healthPlabRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o id %s não encontrada.", id)));

        logger.info("Plano de saúde encontrado :: {} :: iniciando exclusão...", healthPlanEntity.getCommercialName());
        healthPlabRepository.delete(healthPlanEntity);
        logger.info("Exclusão de plano realisada com sucesso!");
    }

}
