package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe SpecialityInfoDTO.java Representa a informação detalhada da especialidade.    
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 10 de jan de 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SpecialityInfoDTO implements Serializable {

    private static final long serialVersionUID = 4419810660458356444L;

    private Long id;

    private String description;

    private List<ResponsibleDTO> responsibles = new ArrayList<>();
}
