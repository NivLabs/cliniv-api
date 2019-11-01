package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import br.com.ft.gdp.models.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe DocumentDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DocumentDTO implements Serializable {

    private static final long serialVersionUID = 8977997168527769344L;

    private DocumentType type;

    private String value;
}
