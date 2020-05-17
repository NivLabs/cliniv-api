package br.com.tl.gdp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe EvolutionInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 5 de dez de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Evolution Informations")
public class EvolutionInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6201888790941764512L;

    private Long id;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime datetime;

}
