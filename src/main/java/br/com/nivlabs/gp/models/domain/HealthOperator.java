package br.com.nivlabs.gp.models.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.Modality;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OPERADORA")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class HealthOperator extends BaseObjectWithId {

    private static final long serialVersionUID = -4784137469843956304L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COD_ANS")
    private Long ansCode;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "RAZAO_SOCIAL")
    private String companyName;

    @Column(name = "NOME_FANTASIA")
    private String fantasyName;

    @Column(name = "MODALIDADE")
    @Enumerated(EnumType.STRING)
    private Modality modality;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "healthOperator")
    private List<HealthPlan> healthPlans = new ArrayList<>();

}
