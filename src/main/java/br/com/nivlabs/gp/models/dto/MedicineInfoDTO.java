package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
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
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações de medicamento")
public class MedicineInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -1687147074910399813L;

    private Long id;
    
    private String code;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime datetime;

    private String description;

    private Double amount;

    private String prescriptionOfficer;

    private String responsibleForTheAdministration;
    
	
	private String anvisaRegistration;
	
	private String presentation;
	
	private String laboratory;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime expirationStartDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime expirationDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime implantationEndDate;
	
}
