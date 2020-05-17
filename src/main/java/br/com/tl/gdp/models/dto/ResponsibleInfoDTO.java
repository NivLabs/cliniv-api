package br.com.tl.gdp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe ResponsibleInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Responsible or professional informations")
public class ResponsibleInfoDTO extends PersonInfoDTO {

    private static final long serialVersionUID = 3558512431533807447L;

    private ProfessionalIdentityDTO professionalIdentity;
    private LocalDateTime createdAt;
    private List<SpecialityDTO> specializations = new ArrayList<>();

}
