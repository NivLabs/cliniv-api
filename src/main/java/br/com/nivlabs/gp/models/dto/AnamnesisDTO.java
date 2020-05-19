package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.AnamnesisItem;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.Patient;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Anamnesis")
public class AnamnesisDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -7700694137849034946L;

    private Long id;

    @NotBlank(message = "Informar o VISITANTE é obrigatório.")
    private Long visitId;

    @NotBlank(message = "Informar o PACIENTE é obrigatório.")
    private Long patientId;

    @NotBlank(message = "Informar o ITEM ANAMNESIS é obrigatório.")
    private AnamnesisItemDTO anamnesisItem;

    @NotBlank(message = "Informar a RESPOSTA é obrigatório.")
    private String response;

    @JsonIgnore
    public Anamnesis getAnamnesesDomainFromDTO() {
        Anamnesis domain = new Anamnesis();

        domain.setId(id);
        domain.setAnamnesisItem(new AnamnesisItem(anamnesisItem.getId(), anamnesisItem.getQuestion(), anamnesisItem.getResponse()));
        domain.setPatient(new Patient(patientId));
        domain.setVisit(new Attendance(visitId));
        domain.setResponse(response);

        return domain;
    }

}
