package br.com.nivlabs.cliniv.service.healthoperator.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.HealthOperator;
import br.com.nivlabs.cliniv.models.dto.HealthOperatorInfoDTO;

/**
 * 
 * Componente específico para atualização cadastral de operadoras de saúde
 *
 * @author viniciosarodrigues
 * @since 05-10-2021
 *
 */
@Component
public class UpdateHealthOperatorBusinessHandler extends CreateHealthOperatorBusinessHandler {

    /**
     * Atualizando informações de operadora de saúde
     * 
     * @param id Identificador único interno da operadora de saúde
     * @param request Objeto da requisição (DTO) de Operadora de saúde
     * @return Objeto atualizado com as novas informações
     */
    public HealthOperatorInfoDTO update(HealthOperatorInfoDTO request) {
        logger.info("Iniciando processo de atualização de cadastro de Operadora/Convênio de Saúde..");
        if (request.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O identificador da operadora não pode ser nulo!");
        }

        HealthOperator healthOperatorEntity = healthOperatorRepository.findById(request.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Operadora com o id %s não encontrada.", request.getId())));

        logger.info("Operadora encontrada {}", healthOperatorEntity.getCompanyName());
        convertRequestToEntity(request);

        logger.info("Salvando atualização de cadastro no banco");
        healthOperatorRepository.save(healthOperatorEntity);

        logger.info("Atualização realizada com sucesso, devolvendo resposta atualizada :: {}", request);
        return request;
    }

}
