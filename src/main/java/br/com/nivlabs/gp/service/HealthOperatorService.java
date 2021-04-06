package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.domain.HealthOperator_;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.repository.HealthOperatorRepository;
import br.com.nivlabs.gp.repository.HealthPlanRepository;

/**
 * 
 * Classe HealthOperatorService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 04 de agosto de 2020
 */
@Service
public class HealthOperatorService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HealthOperatorRepository healthOperatorRepository;

    @Autowired
    private HealthPlanRepository healthPlanRepository;

    /**
     * Busca uma página de operadoras de planos de saúde
     * 
     * @param pageRequest
     * @return Page
     */
    public Page<HealthOperatorDTO> getListOfHealthOperator(HealthOperatorFilters filters, Pageable pageRequest) {
        logger.info("Iniciando busca filtrada por Operadoras de saúde");
        return healthOperatorRepository.resumedList(filters, pageRequest);
    }

    /**
     * Busca os detalhes de uma operadora de plano de saúde
     * 
     * @param id Identificador único da operadora de saúde
     * @return HealthOperatorInfoDTO
     */
    public HealthOperatorInfoDTO findByHealthOperatorId(Long id) {
        logger.info("Iniciando busca de operadora por identificador único interno :: {}", id);
        HealthOperator healthOperator = healthOperatorRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Operadora com o identificador %s não encontrado", id)));
        List<HealthPlan> plans = healthOperator.getHealthPlans();
        List<HealthPlanDTO> plansDTO = new ArrayList<>();

        HealthOperatorInfoDTO healthOperatorInfoDTO = new HealthOperatorInfoDTO();

        BeanUtils.copyProperties(healthOperator, healthOperatorInfoDTO, HealthOperator_.HEALTH_PLANS);
        plans.forEach(plan -> {
            HealthPlanDTO newHealthPlanDTO = new HealthPlanDTO();
            BeanUtils.copyProperties(plan, newHealthPlanDTO);
            plansDTO.add(newHealthPlanDTO);
        });

        healthOperatorInfoDTO.setHealthPlans(plansDTO);
        healthOperatorInfoDTO.setDocument(new DocumentDTO(null, DocumentType.CNPJ, healthOperator.getCnpj(), null, null, null, null));

        return healthOperatorInfoDTO;
    }

    /**
     * Busca o plano de saúde pelo identificador único
     * 
     * @param id Identificador único do plano de saúde
     * @return
     */
    public HealthPlanDTO findHealthPlanById(Long id) {
        logger.info("Iniciando busca de plano de saúde por id :: {}", id);
        if (id == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Informe o identificador único do plano para a pesquisa");
        }
        HealthPlan objectFromDB = healthPlanRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o identificador %s não encontrado", id)));

        return convertSearchHealthPlanObject(objectFromDB);
    }

    /**
     * Busca o plando de saúde pelo código da ANS
     * 
     * @param ansCode Código ANS do plano de saúde
     * @return
     */
    public HealthPlanDTO findHealthPlanByAnsCode(Long ansCode) {
        if (ansCode == null) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "Informe o código da ANS do plano para a pesquisa");
        }
        HealthPlan objectFromDB = healthPlanRepository.findByPlanCode(ansCode)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Plano de saúde com o código ANS %s não encontrado", ansCode)));

        return convertSearchHealthPlanObject(objectFromDB);
    }

    /**
     * Converte um HealthPlan padrão de domínio para um DTO
     * 
     * @param objectFromDB Objeto de domínio
     * @return DTO de resposta
     */
    private HealthPlanDTO convertSearchHealthPlanObject(HealthPlan objectFromDB) {

        HealthPlanDTO response = new HealthPlanDTO();
        BeanUtils.copyProperties(objectFromDB, response);
        response.setOperatorCode(objectFromDB.getHealthOperator().getAnsCode());
        response.setOperatorName(objectFromDB.getHealthOperator().getCompanyName());
        logger.info("Plano encontrado :: {}", response);
        return response;
    }

    /**
     * Cria um novo cadastro de operadora de saúde
     * 
     * @param request Objeto de requisição (DTO) de Operadora de saúde
     * @return Objeto cadastrado na aplicação
     */
    public HealthOperatorInfoDTO create(HealthOperatorInfoDTO request) {
        logger.info("Iniciando processo de criação de cadastro de Operadora/Convênio de Saúde..");
        HealthOperator healthOperator = new HealthOperator();
        BeanUtils.copyProperties(request, healthOperator, HealthOperator_.ID, HealthOperator_.HEALTH_PLANS);
        healthOperator.setCnpj(request.getDocument().getValue());

        logger.info("Salvando criação de cadastro no banco");
        healthOperatorRepository.save(healthOperator);
        request.setId(healthOperator.getId());

        logger.info("Nova operadora cadastrada com sucesso, devolvendo resposta atualizada :: {}", request);
        return request;
    }

    /**
     * Atualizando informações de operadora de saúde
     * 
     * @param id Identificador único interno da operadora de saúde
     * @param request Objeto da requisição (DTO) de Operadora de saúde
     * @return Objeto atualizado com as novas informações
     */
    public HealthOperatorInfoDTO update(Long id, HealthOperatorInfoDTO request) {
        logger.info("Iniciando processo de atualização de cadastro de Operadora/Convênio de Saúde..");

        HealthOperator objectFromDb = healthOperatorRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format("Operadora com o id %s não encontrada.", id)));

        logger.info("Operadora encontrada {}", objectFromDb.getCompanyName());
        BeanUtils.copyProperties(request, objectFromDb, HealthOperator_.ID, HealthOperator_.HEALTH_PLANS);
        objectFromDb.setCnpj(request.getDocument().getValue());

        logger.info("Salvando atualização de cadastro no banco");
        healthOperatorRepository.save(objectFromDb);

        logger.info("Atualização realizada com sucesso, devolvendo resposta atualizada :: {}", request);
        return request;
    }

}
