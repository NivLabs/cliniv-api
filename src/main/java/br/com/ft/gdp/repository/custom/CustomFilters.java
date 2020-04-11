package br.com.ft.gdp.repository.custom;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe base para a criação de filtros de consultas
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomFilters implements Serializable {

    private static final long serialVersionUID = 8995632262865503148L;

    private Integer page = 0;
    private Integer size = 24;
    private String orderBy = "id";
    private String direction = "ASC";

}
