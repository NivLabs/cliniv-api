package br.com.nivlabs.gp.service.healthplan.business;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.repository.HealthPlanRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para operações busca de planos de saúde
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public class SearchHealthPlanBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected HealthPlanRepository healthPlanRepository;

    /**
     * Busca informações do plano de saúde pelo identificador único do plano
     * 
     * @param id Identificador único do plano de saúde
     * @return Informações do plano de saúde
     */
    public HealthPlanDTO byId(Long id) {
        logger.info("Iniciando busca de plano de saúde por id :: {}", id);
        if (id == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Informe o identificador único do plano para a pesquisa");
        }
        HealthPlan healthPlanEntity = healthPlanRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o identificador %s não encontrado", id)));

        return convertEntityToDTO(healthPlanEntity);
    }

    /**
     * Busca informações do plano de saúde pelo código ANS
     * 
     * @param ansCode Código do plano na ANS
     * @return Informações do plano de saúde
     */
    public HealthPlanDTO byANSCode(Long ansCode) {
        if (ansCode == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Informe o código da ANS do plano para a pesquisa");
        }
        HealthPlan healthPlanEntity = healthPlanRepository.findByPlanCode(ansCode)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o código ANS %s não encontrado", ansCode)));

        return convertEntityToDTO(healthPlanEntity);
    }

    /**
     * Converte um HealthPlan padrão de domínio para um DTO
     * 
     * @param healthPlanEntity Objeto de domínio
     * @return DTO de resposta
     */
    private HealthPlanDTO convertEntityToDTO(HealthPlan healthPlanEntity) {

        HealthPlanDTO healthPlanInfo = new HealthPlanDTO();
        BeanUtils.copyProperties(healthPlanEntity, healthPlanInfo);
        healthPlanInfo.setId(healthPlanEntity.getId());
        healthPlanInfo.setOperatorCode(healthPlanEntity.getHealthOperator().getAnsCode());
        healthPlanInfo.setOperatorName(healthPlanEntity.getHealthOperator().getCompanyName());
        healthPlanInfo.setPlanCode(healthPlanEntity.getPlanCode());
        healthPlanInfo.setCommercialName(healthPlanEntity.getCommercialName());
        healthPlanInfo.setSegmentation(healthPlanEntity.getSegmentation());
        healthPlanInfo.setContractType(healthPlanEntity.getContractType());
        healthPlanInfo.setAbrangency(healthPlanEntity.getAbrangency());
        healthPlanInfo.setType(healthPlanEntity.getType());
        logger.info("Plano encontrado :: {}", healthPlanInfo);
        return healthPlanInfo;
    }

}
