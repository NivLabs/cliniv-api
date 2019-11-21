package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.ft.gdp.models.enums.Gender;
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
public class VisitInfoDTO implements Serializable {

    private static final long serialVersionUID = -6838739544914003033L;

    private Long id;

    private Long patientId;

    private DocumentDTO document;

    private String firstName;

    private String lastName;

    private String principalNumber;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date bornDate;

    private Gender gender;

    private List<VisitEventDTO> events = new ArrayList<>();

    private List<String> allergies = new ArrayList<>();

}
