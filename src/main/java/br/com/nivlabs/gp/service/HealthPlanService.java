package br.com.nivlabs.gp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.domain.HealthPlan_;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.repository.HealthOperatorRepository;
import br.com.nivlabs.gp.repository.HealthPlanRepository;

/**
 * Classe HealthPlanService.java
 * 
 * @author viniciosarodrigues
 * @since 25/09/2020
 */
@Service
public class HealthPlanService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HealthPlanRepository principalRepository;
    @Autowired
    private HealthOperatorRepository healthOperatorRepository;

    /**
     * Atualiza os dados de um plano de saúde já cadastrado
     * 
     * @param request Objeto da requisição (Plano de Saúde) para atualização
     * @return Objeto atualizado
     */
    public HealthPlanDTO update(Long id, HealthPlanDTO request) {
        logger.info("Iniciando processo de atualização de cadastro de Plano de Saúde...");

        HealthPlan objectFromDb = principalRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o id %s não encontrada.", id)));
        objectFromDb.setHealthOperator(healthOperatorRepository.findByAnsCode(request.getOperatorCode())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "A Operadora informada no cadastro do Plano não está cadastrada no sistema, favor realizar o cadastro da mesma.")));
        BeanUtils.copyProperties(request, objectFromDb, HealthPlan_.ID);
        principalRepository.save(objectFromDb);

        return request;
    }

    /**
     * Cadastra um novo plano de saúde ao sistema
     * 
     * @param request Objeto a requisição (Plano de Saúde) para criação de cadastro
     * @return Objeto cadastrado
     */
    public HealthPlanDTO create(HealthPlanDTO request) {
        logger.info("Iniciando processo de criação de cadastro de Plano de Saúde...");
        HealthOperator healthOperator = healthOperatorRepository.findByAnsCode(request.getOperatorCode())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "A Operadora informada no cadastro do Plano não está cadastrada no sistema, favor realizar o cadastro da mesma."));

        if (principalRepository.findByPlanCode(request.getPlanCode()).isPresent()) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Este código de Plano de Saúde já está em uso por outro plano");
        }

        HealthPlan healthPlan = new HealthPlan();
        BeanUtils.copyProperties(request, healthPlan);
        healthPlan.setHealthOperator(healthOperator);

        principalRepository.save(healthPlan);

        request.setId(healthOperator.getId());

        return request;
    }

}
