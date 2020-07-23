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
 * Classe FeeDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 22 de jul de 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Taxas")
public class FeeDTO extends DataTransferObjectBase {

	private static final long serialVersionUID = 139714345053962715L;
	
	private String id;
	
	private String code;
	
	private String description;
	
	private String detail;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime expirationStartDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime expirationDate;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime implantationEndDate;
}
