package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.AnamneseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ANAMNESE")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Anamnese extends BaseObject {
    private static final long serialVersionUID = -4203582800741543902L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VISITA")
    private Visit visit;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "ID_ANAMNESE_ITEM")
    private AnamnesisItem anamnesisItem;

    @Column(name = "RESPOSTA")
    private String response;

    @JsonIgnore
    public AnamneseDTO getAnamneseDTOFromDomain() {
        AnamneseDTO dto = new AnamneseDTO();

        dto.setId(id);
        dto.setAnamnesisItem(anamnesisItem);
        dto.setPatient(patient);
        dto.setVisit(visit);
        dto.setResponse(response);

        return dto;
    }
}
