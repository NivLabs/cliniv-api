package br.com.nivlabs.gp.models.dto;

import br.com.nivlabs.gp.enums.PatientType;
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
@ApiModel("Informações do Paciente")
public class PatientDTO extends PersonDTO {

    private static final long serialVersionUID = -1070682704153329772L;

    private String susNumber;

    private PatientType type;
}
