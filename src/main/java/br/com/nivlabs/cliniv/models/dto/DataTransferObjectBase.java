package br.com.nivlabs.cliniv.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Objeto base para todas as requisições e respostas da Aplicação (DTO)
 *
 * @author viniciosarodrigues
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public abstract class DataTransferObjectBase implements Serializable {

}
