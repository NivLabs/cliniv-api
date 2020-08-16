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
import br.com.nivlabs.gp.enums.GenderIdentity;
import br.com.nivlabs.gp.enums.PatientType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("Identificador único do paciente")
    private Long id;

    @ApiModelProperty("Nome completo do paciente")
    @NotNull(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 45, message = "O nome completo é obrigatório")
    private String fullName;

    @ApiModelProperty("Nome social do paciente")
    private String socialName;

    @ApiModelProperty("Data de nascimento")
    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    @ApiModelProperty("Documento do paciente")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    @ApiModelProperty("Gênero (sexo) do paciente")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty("Identidade de gênero (orientação) do paciente")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @ApiModelProperty("Nome do pai do paciente")
    private String fatherName;

    @ApiModelProperty("Nome da mãe do paciente")
    private String motherName;

    @ApiModelProperty("Número de telefone/celular principal do paciente")
    private String principalNumber;

    @ApiModelProperty("Número de telefone/celular secundário do paciente")
    private String secondaryNumber;

    @ApiModelProperty("Endereço do paciente")
    private AddressDTO address;

    @ApiModelProperty("Foto de perfil do paciente")
    private String profilePhoto;

    @ApiModelProperty("Número do SUS do paciente")
    @Size(max = 15, message = "O código do deve conter no máximo 15 dígitos.")
    private String susNumber;

    @ApiModelProperty("Tipo do paciente (Identificado ou não identificado)")
    private PatientType type;

    @ApiModelProperty("Anotações sobre o paciente")
    @Size(max = 300, message = "As anotações não devem passar de 300 caracteres")
    private String annotations;

    @ApiModelProperty("Data de criação do registro")
    private LocalDateTime createdAt;

    @ApiModelProperty("Alergias do pacente")
    private List<String> allergies = new ArrayList<>();

    @ApiModelProperty("Plano de saúde do paciente")
    private HealthPlanDTO healthPlan;
}
