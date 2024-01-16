package br.com.nivlabs.cliniv;

import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.enums.ParameterAliasType;
import br.com.nivlabs.cliniv.models.domain.Parameter;
import br.com.nivlabs.cliniv.models.dto.ParameterDTO;
import br.com.nivlabs.cliniv.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Componente específico para parametrização da aplicação
 *
 * @author viniciosarodrigues
 */
public class AppSettings {

    protected static Map<ParameterAliasType, ParameterDTO> parameters;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Inicializa os parâmetros da aplicação
     *
     * @param parametersList Lista de parâmetros da aplicação
     */
    protected AppSettings(List<Parameter> parametersList) {
        logger.info("Inicializando configurações do a aplicação...");
        parameters = new HashMap<>();

        logger.info("Iniciando lista de parâmetros...");
        parametersList.forEach(parameter -> {
            parameters.put(parameter.getAlias(),
                    new ParameterDTO(parameter.getId(), parameter.getAlias(), parameter.getName(), parameter.getGroup(),
                            parameter.getMetaType(), parameter.getValue(),
                            parameter.getGroupValues() != null ? parameter.getGroupValues().split(";") : null));

            logger.info("Processando :: {}", parameter);
        });
    }

    /**
     * Busca parâmetro por identificador e retorna o valor do mesmo
     *
     * @param id Identificador único do parãmetro
     * @return Valor do parâmetro que neste caso é boolean
     */
    public boolean getBooleanValue(ParameterAliasType alias) {
        var parameter = parameters.get(alias);
        if (parameter.getMetaType() == MetaType.BOOL) {
            if (parameter.getValue() != null)
                return parameter.getValue().equalsIgnoreCase("true");
            return false;
        } else
            throw new IllegalArgumentException(String.format("O tipo de parâmetro não é válido! Tipo esperado :: %s | Tipo recebido :: %s",
                    "true/false", parameter.getValue()));
    }

    /**
     * Busca parâmetro por identificaro e retorna o valor do mesmo
     *
     * @param id Identificador único do parâmetro
     * @return Valor do parâmetro que neste caso é uma String
     */
    public String getStringValue(ParameterAliasType alias) {
        var parameter = parameters.get(alias);
        if (parameter.getMetaType() == MetaType.STRING && !StringUtils.isNullOrEmpty(parameter.getValue())) {
            return parameter.getValue();
        }
        return "";
    }

}
