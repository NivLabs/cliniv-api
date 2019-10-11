package br.com.ft.gdp.models.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.VisitDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Visit.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "VISITA")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Visit extends BaseObject {

    private static final long serialVersionUID = -2728953699232281599L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_PACIENTE")
    private Patient patient;

    @Column(name = "DH_ENTRADA")
    private Date dateTimeEntry;

    @Column(name = "DH_SAIDA")
    private Date dateTimeExit;

    @Column(name = "MOTIVO_ENTRADA")
    private String reasonForEntry;

    @JsonIgnore
    public VisitDTO getVisitDTOFromDomain() {
        VisitDTO dtoEntity = new VisitDTO();

        dtoEntity.setId(getId());
        dtoEntity.setPatient(getPatient());
        dtoEntity.setDateTimeEntry(getDateTimeEntry());
        dtoEntity.setDateTimeExit(getDateTimeExit());
        dtoEntity.setReasonForEntry(getReasonForEntry());
        return dtoEntity;
    }
}
