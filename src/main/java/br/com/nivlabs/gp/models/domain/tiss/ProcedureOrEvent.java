package br.com.nivlabs.gp.models.domain.tiss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import br.com.nivlabs.gp.exception.InvalidOperationException;
import br.com.nivlabs.gp.models.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROCEDIMENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureOrEvent extends BaseObject {

    private static final long serialVersionUID = -7145671144200832961L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "ATIVO")
    private boolean active;

    @PrePersist
    private void blockPersist() {
        throw new InvalidOperationException("Não é permitido a inserção de um novo registro na tabela de procedimentos/eventos");
    }
}
