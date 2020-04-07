package br.com.ft.gdp.controller.utils;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Paginação filtrada para requisições GET paginadas
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilteredPageable extends Pageable {

    private Filters filters;

    @Data
    class Filters implements Serializable {

        private static final long serialVersionUID = 4564929444696269698L;

    }
}
