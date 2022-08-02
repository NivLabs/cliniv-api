package br.com.nivlabs.cliniv.service.parameter.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Parameter;
import br.com.nivlabs.cliniv.models.dto.NewParameterValueDTO;
import br.com.nivlabs.cliniv.repository.ParameterRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * 
 * Componente específico para alteração de valor de parâmetro da aplicação
 *
 * @author viniciosarodrigues
 * @since 30-09-2021
 *
 */
@Component
public class UpdateParameterBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected ParameterRepository repository;

    /**
     * Atualiza valor de um determinado parâmetro
     * 
     * @param id Identificador único do parâmetro
     * @param newValue Requisição de mudança de valor de parâmetro
     */
    public void updateValue(Long id, NewParameterValueDTO newValue) {
        if (newValue == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O novo valor não pode ser nulo");
        }
        logger.info("Iniciando busca de parâmetro para o ID :: {}", id);
        Parameter parameter = repository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Parâmetro com identificador %s não encontrado", id)));
        logger.info("Parãmetro encontrado :: {}", parameter.getName());
        checkParameterValue(parameter, newValue.getNewValue());
        parameter.setValue(newValue.getNewValue());
        if (!parameter.getMetaType().equals(MetaType.PASSWORD))
            logger.info("Novo valor do parâmetro :: {}", newValue);
        else
            logger.info("O novo valor pertence à um parâmetro do tipo password, não será exibido.");
        repository.save(parameter);
    }

    /**
     * Checa os tipos de parâmetros e o novo valor
     * 
     * @param param Parâmetro
     * @param newValue Novo valor do parâmetro
     */
    private void checkParameterValue(Parameter param, String newValue) {
        logger.info("Checando valor para parâmetro do tipo {}", param.getMetaType());
        switch (param.getMetaType()) {
            case NUMBER:
                if (!StringUtils.isNumeric(newValue))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor do parâmetro deve ser numérico");
                break;
            case BOOL:
                if (StringUtils.isNullOrEmpty(newValue)
                        || (!newValue.equalsIgnoreCase("true") && !newValue.equalsIgnoreCase("false")))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor do parâmetro só pode ser true ou false");
                break;
            case GROUP:
                if (StringUtils.isNullOrEmpty(newValue) || checkGroupParameter(param.getGroupValues(), newValue))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor do parâmetro deve existir no grupo de valores possíveis");
                break;
            case STRING, PASSWORD:
                logger.info("Parâmetro de valor textual... Não há validação para o mesmo.");
                break;
            default:
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "O valor do parâmetro é inválido para o tipo de parâmetro");
        }
        logger.info("Chegagem realizada com sucesso, o novo valor é válido para subistituição");
    }

    /**
     * Checa o valor do parâmetro de grupo
     * 
     * @param groupValues Valores do grupo
     * @param value Valor enviado para validação no grupo
     * @return Válido ou não
     */
    private boolean checkGroupParameter(String groupValues, String parameterValue) {
        for (String value : groupValues.split(";"))
            if (value.equals(parameterValue))
                return true;
        return false;
    }
}
