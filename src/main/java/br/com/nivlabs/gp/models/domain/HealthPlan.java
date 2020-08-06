package br.com.nivlabs.gp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.Abragency;
import br.com.nivlabs.gp.enums.ContractType;
import br.com.nivlabs.gp.enums.Segmentation;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLANO")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class HealthPlan extends BaseObjectWithId {

    private static final long serialVersionUID = -7856635198888818925L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COD_PLAN")
    private Long planCode;

    @Column(name = "NOME_COMERCIAL")
    private String commercialName;

    @Column(name = "SEGMENTACAO")
    @Enumerated(EnumType.STRING)
    private Segmentation segmentation;

    @Column(name = "TIPO_CONTRATO")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(name = "ABRANGENCIA")
    @Enumerated(EnumType.STRING)
    private Abragency abragency;

    @Column(name = "TIPO_PLANO")
    private String type;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OPERADORA")
    private HealthOperator healthOperator;

}
