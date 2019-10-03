package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import br.com.ft.gdp.models.domain.Anamnese;
import br.com.ft.gdp.models.domain.AnamnesisItem;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AnamneseDTO implements Serializable {
    private static final long serialVersionUID = -7700694137849034946L;

    private Long id;

    @NotBlank(message = "Informar o VISITANTE é obrigatório.")
    private Visit visit;

    @NotBlank(message = "Informar o PACIENTE é obrigatório.")
    private Patient patient;

    @NotBlank(message = "Informar o ITEM ANAMNESIS é obrigatório.")
    private AnamnesisItem anamnesisItem;

    @NotBlank(message = "Informar a RESPOSTA é obrigatório.")
    private String response;

    public Anamnese getAnamnesesDomainFromDTO() {
        Anamnese domain = new Anamnese();

        domain.setId(id);
        domain.setAnamnesisItem(anamnesisItem);
        domain.setPatient(patient);
        domain.setVisit(visit);
        domain.setResponse(response);

        return domain;
    }

}
