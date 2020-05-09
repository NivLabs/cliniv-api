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
import br.com.ft.gdp.models.dto.AnamnesisDTO;
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
public class Anamnesis extends BaseObject {
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
    public AnamnesisDTO getAnamneseDTOFromDomain() {
        AnamnesisDTO dto = new AnamnesisDTO();

        dto.setId(id);
        dto.setAnamnesisItem(anamnesisItem.getAnamnesisItemDTOFromDomain());
        dto.setPatientId(patient.getId());
        dto.setVisitId(visit.getId());
        dto.setResponse(response);

        return dto;
    }
}
