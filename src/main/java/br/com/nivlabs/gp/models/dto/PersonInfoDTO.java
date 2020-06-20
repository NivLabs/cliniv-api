package br.com.nivlabs.gp.models.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.models.enums.Gender;
import br.com.nivlabs.gp.models.enums.GenderIdeology;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PersonInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações detalhadas da pessoa")
public abstract class PersonInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4735834196671409605L;

    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(min = 3, max = 45, message = "O nome é obrigatório")
    private String firstName;

    @NotNull(message = "O sobrenome é obrigatório")
    @Size(min = 3, max = 45, message = "O sobrenome é obrigatório")
    private String lastName;

    @NotNull(message = "A data de nascimento é obrigatória")
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

}
