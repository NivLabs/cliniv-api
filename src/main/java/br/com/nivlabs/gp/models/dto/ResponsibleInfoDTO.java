package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe ResponsibleInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@ApiModel(description = "Responsible or professional informations")
public class ResponsibleInfoDTO extends PersonInfoDTO {

    private static final long serialVersionUID = 3558512431533807447L;

    @ApiModelProperty("Registro profissional do responsável (Se houver)")
    private ProfessionalIdentityDTO professionalIdentity;

    @ApiModelProperty("Data / Hora de criação")
    private LocalDateTime createdAt;

    @ApiModelProperty("Especializaçõs do responsável (Se houver)")
    private List<SpecialityDTO> specializations = new ArrayList<>();

    public ProfessionalIdentityDTO getProfessionalIdentity() {
        return professionalIdentity;
    }

    public void setProfessionalIdentity(ProfessionalIdentityDTO professionalIdentity) {
        this.professionalIdentity = professionalIdentity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<SpecialityDTO> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<SpecialityDTO> specializations) {
        this.specializations = specializations;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponsibleInfoDTO [professionalIdentity=");
        builder.append(professionalIdentity);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append(", specializations=");
        builder.append(specializations);
        builder.append("]");
        return builder.toString();
    }

}
