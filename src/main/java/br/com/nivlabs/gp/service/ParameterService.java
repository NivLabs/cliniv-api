package br.com.nivlabs.gp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Parameter;
import br.com.nivlabs.gp.models.dto.NewParameterValueDTO;
import br.com.nivlabs.gp.models.enums.MetaType;
import br.com.nivlabs.gp.repository.ParameterRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Trata manipulação de parâmetros da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ParameterService implements GenericService<Parameter, Long> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParameterRepository repository;

    /**
     * Altera o valor do parâmetro
     * 
     * @param id
     * @param newValue
     */
    public void changeValueParameter(Long id, NewParameterValueDTO newValue) {
        if (newValue == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O novo valor não pode ser nulo");
        }
        logger.info("Iniciando busca de parâmetro para o ID :: {}", id);
        Parameter parameter = repository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Parâmetro com identificador %s não encontrado", id)));
        logger.info("Parãmetro encontrado :: {}", parameter.getName());
        checkParameterValue(parameter, newValue.getNewValue());
        parameter.setValue(newValue.getNewValue());
        if (!parameter.getMetaType().equals(MetaType.password))
            logger.info("Novo valor do parâmetro :: {}", newValue);
        else
            logger.info("O novo valor pertence à um parâmetro do tipo password, não será exibido.");
        repository.save(parameter);
    }

    /**
     * Checa os tipos de parâmetros e o novo valor
     * 
     * @param param
     * @param newValue
     */
    private void checkParameterValue(Parameter param, String newValue) {
        logger.info("Checando valor para parâmetro do tipo {}", param.getMetaType());
        switch (param.getMetaType()) {
            case number:
                if (!StringUtils.isNumeric(newValue))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor do parâmetro deve ser numérico");
                break;
            case bool:
                if (StringUtils.isNullOrEmpty(newValue)
                        || (!newValue.equalsIgnoreCase("true") && !newValue.equalsIgnoreCase("false")))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor do parâmetro só pode ser true ou false");
                break;
            case group:
                if (StringUtils.isNullOrEmpty(newValue) || checkGroupParameter(newValue))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "O valor do parâmetro deve existir no grupo de valores possíveis");
                break;
            case string:
            case password:
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
     * @param param
     * @param newValue
     * @return
     */
    private boolean checkGroupParameter(String newValue) {
        for (String value : newValue.split(";"))
            if (value.equals(newValue))
                return true;
        return false;
    }

}
