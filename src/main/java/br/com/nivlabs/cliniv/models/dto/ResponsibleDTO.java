package br.com.nivlabs.cliniv.models.dto;

import br.com.nivlabs.cliniv.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ResponsibleDTO.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 7 de set de 2019
 */
@Schema(description = "Profissional ou responsável")
public class ResponsibleDTO extends PersonDTO {

    private String professionalIdentity;

    private String initialsIdentity;

    private List<SpecialityDTO> specializations = new ArrayList<>();

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
    public String toString() {
        return "ResponsibleDTO{" +
                "professionalIdentity='" + professionalIdentity + '\'' +
                ", initialsIdentity='" + initialsIdentity + '\'' +
                ", specializations=" + specializations +
                '}';
    }
}
