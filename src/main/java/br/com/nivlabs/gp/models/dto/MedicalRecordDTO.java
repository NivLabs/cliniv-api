package br.com.nivlabs.gp.models.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.models.enums.Gender;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um prontuário médico
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 11 de out de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Medical record")
public class MedicalRecordDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6838739544914003033L;

    private Long id;

    private Long patientId;

    private DocumentDTO document;

    private String firstName;

    private String lastName;

    private String principalNumber;

    private String susNumber;

    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    private Gender gender;

    private List<AttendanceEventDTO> events = new ArrayList<>();

    private List<MedicineInfoDTO> medicines = new ArrayList<>();

    private List<EvolutionInfoDTO> evolutions = new ArrayList<>();

    private List<String> allergies = new ArrayList<>();

}
