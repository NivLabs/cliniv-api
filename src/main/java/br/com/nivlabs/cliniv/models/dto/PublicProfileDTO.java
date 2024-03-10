package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "Perfil público do profissional")
public class PublicProfileDTO extends DataTransferObjectBase {

    @Schema(description = "Título do profissional")
    private String title;
    @Schema(description = "Informações de redes sociais")
    private SocialDTO social;
    @Schema(description = "Público alvo")
    private Set<TargetAudienceDTO> audiences = new HashSet<TargetAudienceDTO>();
    @Schema(description = "Serviços disponíveis")
    private Set<AvailableServiceDTO> services = new HashSet<AvailableServiceDTO>();
    @Schema(description = "Convênios aceitos")
    private Set<AcceptedInsurancePlanDTO> insurancePlans = new HashSet<AcceptedInsurancePlanDTO>();
    private String observations;

    public PublicProfileDTO() {
    }

    public PublicProfileDTO(String title, SocialDTO social, Set<TargetAudienceDTO> audiences, Set<AvailableServiceDTO> services, Set<AcceptedInsurancePlanDTO> insurancePlans, String observations) {
        this.title = title;
        this.social = social;
        this.audiences = audiences;
        this.services = services;
        this.insurancePlans = insurancePlans;
        this.observations = observations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SocialDTO getSocial() {
        return social;
    }

    public void setSocial(SocialDTO social) {
        this.social = social;
    }

    public Set<TargetAudienceDTO> getAudiences() {
        return audiences;
    }

    public void setAudiences(Set<TargetAudienceDTO> audiences) {
        this.audiences = audiences;
    }

    public Set<AvailableServiceDTO> getServices() {
        return services;
    }

    public void setServices(Set<AvailableServiceDTO> services) {
        this.services = services;
    }

    public Set<AcceptedInsurancePlanDTO> getInsurancePlans() {
        return insurancePlans;
    }

    public void setInsurancePlans(Set<AcceptedInsurancePlanDTO> insurancePlans) {
        this.insurancePlans = insurancePlans;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "PublicProfileDTO{" +
                "title='" + title + '\'' +
                ", social=" + social +
                ", audiences=" + audiences +
                ", services=" + services +
                ", insurancePlans=" + insurancePlans +
                ", observations='" + observations + '\'' +
                '}';
    }
}
