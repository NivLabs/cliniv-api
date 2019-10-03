package br.com.ft.gdp.models.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PatientPhone.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de out de 2019
 */

@Entity
@Table(name = "TELEFONE")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PatientPhone implements Serializable {

    private static final long serialVersionUID = -3625278264448753918L;

    @Id
    @Column(name = "ID_PACIENTE")
    private Long patientId;

    @Id
    @Column(name = "TELEFONE")
    private String phoneNumber;

}
