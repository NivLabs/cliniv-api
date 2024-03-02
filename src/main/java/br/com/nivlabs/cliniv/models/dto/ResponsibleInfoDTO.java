package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Informações do responsável  ou profissional")
public class ResponsibleInfoDTO extends PersonInfoDTO {

    @Schema(description = "Registro profissional do responsável (Se houver)")
    private ProfessionalIdentityDTO professionalIdentity;

    @Schema(description = "Data / Hora de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Especializaçõs do responsável (Se houver)")
    private List<SpecialityDTO> specializations = new ArrayList<>();

    @Schema(description = "Perfil público do profissional")
    private PublicProfileDTO publicProfile;


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

    public PublicProfileDTO getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(PublicProfileDTO publicProfile) {
        this.publicProfile = publicProfile;
    }

    @Override
    public String toString() {
        return "ResponsibleInfoDTO{" +
                "professionalIdentity=" + professionalIdentity +
                ", createdAt=" + createdAt +
                ", specializations=" + specializations +
                ", publicProfile=" + publicProfile +
                '}';
    }
}