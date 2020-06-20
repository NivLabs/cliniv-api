package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PACIENTE_ALERGIA")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PatientAllergyID.class)
public class PatientAllergy extends BaseObject {

    private static final long serialVersionUID = -727231950405483043L;

    @Id
    @Column(name = "ID_PACIENTE")
    private Long patientId;

    @Id
    @Column(name = "DESCRICAO")
    private String description;
}
