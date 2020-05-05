package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Representa a situação atual da API
 * 
 * @author viniciosarodrigues
 *
 */
@Data
public class ServerStatusDTO implements Serializable {
    private static final long serialVersionUID = -8293328535500084535L;

    private Date currentDate = new Date();
    private String applicationName = "API Gestão de Prontuário";
    private String version = "DEV";
    private String status = "Ligado";
}
