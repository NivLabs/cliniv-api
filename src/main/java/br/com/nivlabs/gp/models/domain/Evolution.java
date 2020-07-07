package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.models.BaseObjectWithId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade relacional que representa a evolução clínica do paciente no banco de dados
 * 
 * @author viniciosarodrigues
 *
 */
@Entity
@Table(name = "EVOLUCAO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Evolution extends BaseObjectWithId {

    private static final long serialVersionUID = 2220797718806275886L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "DH_EVOLUCAO")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

}
