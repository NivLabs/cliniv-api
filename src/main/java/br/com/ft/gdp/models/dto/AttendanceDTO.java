package br.com.ft.gdp.models.dto;

import java.time.LocalDateTime;

import br.com.ft.gdp.models.domain.PatientType;
import br.com.ft.gdp.models.enums.EntryType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de nov de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Attendance")
public class AttendanceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -7717106082371494163L;

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDateTime entryDatetime;

    private String entryCause;

    private Boolean isFinished;

    private EntryType type;

    private PatientType patientType;

    private Long patientId;

    private String susNumber;

    private String sectorDescription;
}
