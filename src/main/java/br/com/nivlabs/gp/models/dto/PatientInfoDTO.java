package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.GenderIdeology;
import br.com.nivlabs.gp.enums.PatientType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe NewPatientDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de out de 2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações detalhadas do paciente")
public class PatientInfoDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = 1575416178033511932L;

    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 3, max = 45, message = "O nome é obrigatório")
    private String firstName;

    private String lastName;

    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private GenderIdeology genderIdeology;

    private String fatherName;

    private String motherName;

    private String principalNumber;

    private String secondaryNumber;

    private AddressDTO address;

    private String profilePhoto;

    @Size(max = 15, message = "Informe o código SUS do paciente. O número deve conter ao menos 15 dígitos.")
    private String susNumber;

    private PatientType type;

    @Size(max = 300, message = "As anotações não devem passar de 300 caracteres")
    private String annotations;

    private LocalDateTime createdAt;

    private List<String> allergies = new ArrayList<>();
}
