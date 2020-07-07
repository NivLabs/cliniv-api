package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.MetaType;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@ToString
@Table(name = "APP_PARAM")
public class Parameter extends BaseObjectWithId {

    private static final long serialVersionUID = 3908449162745360425L;

    @Id
    private Long id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "GRUPO")
    private String group;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private MetaType metaType;

    @Column(name = "VALOR")
    private String value;

    @Column(name = "VALORES_DE_GRUPO")
    private String groupValues;

}
