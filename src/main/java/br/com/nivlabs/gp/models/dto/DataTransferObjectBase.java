package br.com.nivlabs.gp.models.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Objeto base para todas as requisições e respostas da Aplicação (DTO)
 * 
 * @author viniciosarodrigues
 *
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public abstract class DataTransferObjectBase implements Serializable {

    private static final long serialVersionUID = 8601475769419769218L;

}
