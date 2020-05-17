package br.com.tl.gdp.models.dto;

import br.com.tl.gdp.models.domain.PatientType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe PatientDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Patient")
public class PatientDTO extends PersonDTO {

    private static final long serialVersionUID = -1070682704153329772L;

    private String susNumber;

    private PatientType type;
}
