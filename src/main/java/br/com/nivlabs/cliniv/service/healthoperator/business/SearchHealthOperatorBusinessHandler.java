package br.com.nivlabs.cliniv.service.healthoperator.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.HealthOperator;
import br.com.nivlabs.cliniv.models.domain.HealthPlan;
import br.com.nivlabs.cliniv.models.dto.DocumentDTO;
import br.com.nivlabs.cliniv.models.dto.HealthOperatorDTO;
import br.com.nivlabs.cliniv.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.cliniv.models.dto.HealthPlanDTO;
import br.com.nivlabs.cliniv.repository.HealthOperatorRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente responsável por realizar buscas de planos de saúde
 *
 * @author viniciosarodrigues
 * @since 04-10-2021
 *
 */
@Component
public class SearchHealthOperatorBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected HealthOperatorRepository healthOperatorRepository;

    /**
     * Busca uma página de operadoras de planos de saúde
     * 
     * @param filters Filtros de busca
     * @param pageRequest Configurações de paginação
     * @return Página de operadoras de planos de saúde
     */
    public Page<HealthOperatorDTO> getPage(HealthOperatorFilters filters, Pageable pageRequest) {
        logger.info("Iniciando busca filtrada por Operadoras de saúde");
        return healthOperatorRepository.resumedList(filters, pageRequest);
    }

    /**
     * Busca os detalhes de uma operadora de plano de saúde à partir do identificador único
     * 
     * @param id Identificador único da operadora de saúde
     * @return Informações detalhadas da operadora de saúde
     */
    @Transactional
    public HealthOperatorInfoDTO byId(Long id) {
        logger.info("Iniciando busca de operadora por identificador único interno :: {}", id);
        HealthOperator healthOperatorEntity = healthOperatorRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Operadora com o identificador %s não encontrado", id)));

        List<HealthPlan> plans = healthOperatorEntity.getHealthPlans();

        HealthOperatorInfoDTO healthOperateorInfo = convertEntityToDTO(healthOperatorEntity);

        plans.forEach(healthPlanEntity -> healthOperateorInfo.getHealthPlans().add(convertEntityToDTO(healthPlanEntity)));

        return healthOperateorInfo;
    }

    /**
     * Converte entidade em objeto de transferência
     * 
     * @param healthPlanEntity Objeto que representa uma entidade relacional de plano de saúde
     * @return Objeto que representa o DTO de plano de saúde
     */
    @Transactional
    private HealthPlanDTO convertEntityToDTO(HealthPlan healthPlanEntity) {
        HealthPlanDTO healthPlanInfo = new HealthPlanDTO();
        healthPlanInfo.setId(healthPlanEntity.getId());
        healthPlanInfo.setOperatorCode(healthPlanEntity.getHealthOperator().getAnsCode());
        healthPlanInfo.setOperatorName(healthPlanEntity.getHealthOperator().getCompanyName());
        healthPlanInfo.setPlanCode(healthPlanEntity.getPlanCode());
        healthPlanInfo.setCommercialName(healthPlanEntity.getCommercialName());
        healthPlanInfo.setSegmentation(healthPlanEntity.getSegmentation());
        healthPlanInfo.setContractType(healthPlanEntity.getContractType());
        healthPlanInfo.setAbrangency(healthPlanEntity.getAbrangency());
        healthPlanInfo.setType(healthPlanEntity.getType());
        return healthPlanInfo;
    }

    /**
     * Converte entidade em objeto de transferência
     * 
     * @param healthOperatorEntity Objeto que representa uma entidade relacional de operadora de saúde
     * @return Objeto que representa o DTO de operadora de sáude
     */
    @Transactional
    private HealthOperatorInfoDTO convertEntityToDTO(HealthOperator healthOperatorEntity) {
        HealthOperatorInfoDTO healthOperatorInfo = new HealthOperatorInfoDTO();
        healthOperatorInfo.setId(healthOperatorEntity.getId());
        healthOperatorInfo.setCompanyName(healthOperatorEntity.getCompanyName());
        healthOperatorInfo.setAnsCode(healthOperatorEntity.getAnsCode());
        healthOperatorInfo.setFantasyName(healthOperatorEntity.getFantasyName());
        healthOperatorInfo.setModality(healthOperatorEntity.getModality());
        healthOperatorInfo.setDocument(new DocumentDTO(DocumentType.CNPJ, healthOperatorEntity.getCnpj()));
        healthOperatorInfo.setHealthPlans(new ArrayList<>());
        return healthOperatorInfo;
    }
}
