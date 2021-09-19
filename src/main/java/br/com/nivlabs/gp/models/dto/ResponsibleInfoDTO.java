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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((professionalIdentity == null) ? 0 : professionalIdentity.hashCode());
        result = prime * result + ((specializations == null) ? 0 : specializations.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResponsibleInfoDTO other = (ResponsibleInfoDTO) obj;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (professionalIdentity == null) {
            if (other.professionalIdentity != null)
                return false;
        } else if (!professionalIdentity.equals(other.professionalIdentity))
            return false;
        if (specializations == null) {
            if (other.specializations != null)
                return false;
        } else if (!specializations.equals(other.specializations))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ResponsibleInfoDTO [professionalIdentity=" + professionalIdentity + ", createdAt=" + createdAt
                + ", specializations=" + specializations + "]";
    }

}
