package br.com.nivlabs.gp.models.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class AllergyID implements Serializable {

    private static final long serialVersionUID = 120177627974976687L;

    private Long id;

    private String description;

}
