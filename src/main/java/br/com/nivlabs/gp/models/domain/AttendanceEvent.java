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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.nivlabs.gp.models.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitEvent.java
 * 
 * @version 1.0
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 * @since 08 Sept, 2019
 * 
 * @version 2.0
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 15 Dez, 2019
 */
@Entity
@Table(name = "VISITA_EVENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceEvent extends BaseObject {

    private static final long serialVersionUID = 8988537898462013276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACIENTE")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    private EventType eventType;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsible responsible;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @Column(name = "ID_DOCUMENTO_DIGITAL")
    private Long documentId;

    @Column(name = "TITULO")
    private String title;

    @Column(name = "OBSERVACOES")
    private String observations;

    @Column(name = "DH_EVENTO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDateTime;

    @PrePersist
    public void prePersist() {
        final LocalDateTime now = LocalDateTime.now();
        this.eventDateTime = now;
    }

}
