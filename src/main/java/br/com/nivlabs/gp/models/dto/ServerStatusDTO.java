package br.com.nivlabs.gp.models.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("Data / Hora atual do servidor")
    private Date currentDate = new Date();

    @ApiModelProperty("Nonme da aplicação")
    private String applicationName = "API Gestão de Prontuário";

    @ApiModelProperty("Veersão da aplicação")
    private String version = "DEV";

    @ApiModelProperty("Status do servidor")
    private String status = "Ligado";
}
