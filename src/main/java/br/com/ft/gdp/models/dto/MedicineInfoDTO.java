package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe MedicineDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 5 de dez de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MedicineInfoDTO implements Serializable {

    private static final long serialVersionUID = -1687147074910399813L;

    private Long id;

    private LocalDateTime datetime;

    private String description;

    private Double amount;

    private String prescriptionOfficer;

    private String responsibleForTheAdministration;
}
