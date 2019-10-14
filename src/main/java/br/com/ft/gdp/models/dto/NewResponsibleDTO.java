package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe NewResponsibleDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 13 de out de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NewResponsibleDTO implements Serializable {

    private static final long serialVersionUID = 6040676863976066146L;

    @NotNull(message = "O nome do responsável é obrigatório")
    private String name;

    @NotNull(message = "A identidade do profissional é obrigatória")
    private String professionalIdentity;

    private Set<Long> especialityIdsList = new HashSet<>();
}
