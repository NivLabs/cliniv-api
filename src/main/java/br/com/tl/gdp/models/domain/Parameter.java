package br.com.tl.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.tl.gdp.models.BaseObject;
import br.com.tl.gdp.models.enums.MetaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "APP_PARAM")
public class Parameter extends BaseObject {

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
