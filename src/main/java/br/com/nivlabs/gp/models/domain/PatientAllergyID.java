package br.com.nivlabs.gp.models.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientAllergyID implements Serializable {

    private static final long serialVersionUID = -7039331290753025629L;

    private Long patientId;

    private String description;
}
