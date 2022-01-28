package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.nivlabs.gp.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe ResponsibleDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Schema(description = "Profissional ou responsável")
public class ResponsibleDTO extends PersonDTO {
    private static final long serialVersionUID = -5141572031863436326L;

    private String professionalIdentity;

    private String initialsIdentity;

    private List<SpecialityDTO> specializations = new ArrayList<>();

    /**
     * Construtor padrão para paginação
     * 
     * @param id
     * @param fullName
     * @param socialName
     * @param cpf
     * @param bornDate
     * @param principalNumber
     * @param gender
     * @param professionalIdentity
     * @param initialsIdentity
     */
    public ResponsibleDTO(Long id, String fullName, String socialName, String cpf, LocalDate bornDate, String principalNumber,
            Gender gender, String professionalIdentity, String initialsIdentity) {
        super();
        super.setId(id);
        super.setFullName(fullName);
        super.setSocialName(socialName);
        super.setCpf(cpf);
        super.setBornDate(bornDate);
        super.setPrincipalNumber(principalNumber);
        super.setGender(gender);
        this.professionalIdentity = professionalIdentity;
        this.initialsIdentity = initialsIdentity;
    }

    public ResponsibleDTO() {
        super();
    }

    public String getProfessionalIdentity() {
        return professionalIdentity;
    }

    public void setProfessionalIdentity(String professionalIdentity) {
        this.professionalIdentity = professionalIdentity;
    }

    public String getInitialsIdentity() {
        return initialsIdentity;
    }

    public void setInitialsIdentity(String initialsIdentity) {
        this.initialsIdentity = initialsIdentity;
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
        result = prime * result + ((initialsIdentity == null) ? 0 : initialsIdentity.hashCode());
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
        ResponsibleDTO other = (ResponsibleDTO) obj;
        if (initialsIdentity == null) {
            if (other.initialsIdentity != null)
                return false;
        } else if (!initialsIdentity.equals(other.initialsIdentity))
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
        return "ResponsibleDTO [professionalIdentity=" + professionalIdentity + ", initialsIdentity=" + initialsIdentity
                + ", specializations=" + specializations + "]";
    }

}
