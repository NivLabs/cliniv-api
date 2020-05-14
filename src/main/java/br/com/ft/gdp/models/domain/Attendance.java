package br.com.ft.gdp.models.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.enums.EntryType;
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
public class Attendance extends BaseObject {

    private static final long serialVersionUID = -2728953699232281599L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @Column(name = "DH_ENTRADA")
    private LocalDateTime dateTimeEntry;

    @Column(name = "DH_SAIDA")
    private LocalDateTime dateTimeExit;

    @Column(name = "TIPO_ENTRADA")
    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @ManyToOne
    @JoinColumn(name = "SETOR_ATUAL")
    private Sector sector;

    @Column(name = "MOTIVO_ENTRADA")
    private String reasonForEntry;

    public Attendance(Long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.dateTimeEntry = now;
    }

}
