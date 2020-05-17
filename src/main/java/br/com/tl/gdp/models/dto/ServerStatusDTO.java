package br.com.tl.gdp.models.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa a situação atual da API
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("API Status")
public class ServerStatusDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -8293328535500084535L;

    private Date currentDate = new Date();
    private String applicationName = "API Gestão de Prontuário";
    private String version = "DEV";
    private String status = "Ligado";
}
