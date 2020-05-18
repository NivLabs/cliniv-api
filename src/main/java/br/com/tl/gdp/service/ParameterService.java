package br.com.tl.gdp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tl.gdp.exception.ObjectNotFoundException;
import br.com.tl.gdp.exception.ValidationException;
import br.com.tl.gdp.models.domain.Parameter;
import br.com.tl.gdp.models.enums.MetaType;
import br.com.tl.gdp.repository.ParameterRepository;
import br.com.tl.gdp.util.StringUtils;

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
    public void changeValueParameter(Long id, String newValue) {
        logger.info("Iniciando busca de parâmetro para o ID :: {}", id);
        Parameter parameter = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Parâmetro com identificador %s não encontrado", id)));
        logger.info("Parãmetro encontrado :: {}", parameter.getName());
        checkParameterValue(parameter, newValue);
        parameter.setValue(newValue);
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
        logger.info("Checando valor para parâmetro do tipo {}", param.getMetaType().toString());
        switch (param.getMetaType()) {
            case number:
                if (!StringUtils.isNumeric(newValue))
                    throw new ValidationException("O valor do parâmetro deve ser numérico");
                break;
            case bool:
                if (StringUtils.isNullOrEmpty(newValue)
                        || (!newValue.toLowerCase().equals("true") && !newValue.toLowerCase().equals("false")))
                    throw new ValidationException("O valor do parâmetro só pode ser true ou false");
                break;
            case group:
                if (StringUtils.isNullOrEmpty(newValue) || checkGroupParameter(param, newValue))
                    throw new ValidationException("O valor do parâmetro deve existir no grupo de valores possíveis");
                break;
            default:
                throw new ValidationException("O valor do parâmetro é inválido para o tipo de parâmetro");
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
    private boolean checkGroupParameter(Parameter param, String newValue) {
        for (String value : newValue.split(";"))
            if (value.equals(newValue))
                return true;
        return false;
    }

}
