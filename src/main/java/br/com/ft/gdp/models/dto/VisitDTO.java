package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.ft.gdp.models.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 11 de out de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VisitDTO implements Serializable {

    private static final long serialVersionUID = -6838739544914003033L;

    private Long id;

    private Patient patient;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date dateTimeEntry;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date dateTimeExit;

    private String reasonForEntry;

}
