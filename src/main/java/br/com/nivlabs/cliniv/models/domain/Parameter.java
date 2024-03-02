package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.enums.ParameterAliasType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "APP_PARAM")
public class Parameter extends BaseObjectWithId<Long> {

    @Id
    private Long id;

    @Column(name = "ALIAS")
    @Enumerated(EnumType.STRING)
    private ParameterAliasType alias;

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

    public Parameter() {
        super();
    }

    public Parameter(Long id, ParameterAliasType alias, String name, String group, MetaType metaType, String value, String groupValues) {
        super();
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.group = group;
        this.metaType = metaType;
        this.value = value;
        this.groupValues = groupValues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ParameterAliasType getAlias() {
        return alias;
    }

    public void setAlias(ParameterAliasType alias) {
        this.alias = alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public MetaType getMetaType() {
        return metaType;
    }

    public void setMetaType(MetaType metaType) {
        this.metaType = metaType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGroupValues() {
        return groupValues;
    }

    public void setGroupValues(String groupValues) {
        this.groupValues = groupValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(id, parameter.id) && alias == parameter.alias && Objects.equals(name, parameter.name) && Objects.equals(group, parameter.group) && metaType == parameter.metaType && Objects.equals(value, parameter.value) && Objects.equals(groupValues, parameter.groupValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alias, name, group, metaType, value, groupValues);
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", alias=" + alias +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", metaType=" + metaType +
                ", value='" + value + '\'' +
                ", groupValues='" + groupValues + '\'' +
                '}';
    }

}
