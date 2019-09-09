package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
* Classe VisitDTO.java
*
* @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
*
* @since 8 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VisitDTO implements Serializable {

	private static final long serialVersionUID = 2370290606342755763L;

    private Long id;

    @NotBlank(message = "Informar o PACIENTE  da visita é obrigatório")
    private Patient patient;
    
    @NotBlank(message = "Informar a DATA de inicio da visita é obrigatório")
    private Date dateTimeEntry;
    
    private Date dateTimeExit;
    
    @NotBlank(message = "Informar o MOTIVO da visita é obrigatório")
    private String reasonForEntry;

    @JsonIgnore
    public Visit getVisitDomainFromDTO() {
    	Visit domain = new Visit();
    	
        domain.setId(getId());
        domain.setPatient(getPatient());
        domain.setDateTimeEntry(getDateTimeEntry());
        domain.setDateTimeExit(getDateTimeExit());
        domain.setReasonForEntry(getReasonForEntry());
        return domain;
    }
}
