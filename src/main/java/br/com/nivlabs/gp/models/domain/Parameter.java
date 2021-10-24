package br.com.nivlabs.gp.models.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.MetaType;
import br.com.nivlabs.gp.enums.ParameterAliasType;
import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "APP_PARAM")
public class Parameter extends BaseObjectWithId {

    private static final long serialVersionUID = 3908449162745360425L;

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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Parameter [id=");
        builder.append(id);
        builder.append(", alias=");
        builder.append(alias);
        builder.append(", name=");
        builder.append(name);
        builder.append(", group=");
        builder.append(group);
        builder.append(", metaType=");
        builder.append(metaType);
        builder.append(", value=");
        builder.append(value);
        builder.append(", groupValues=");
        builder.append(groupValues);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, group, groupValues, id, metaType, name, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Parameter other = (Parameter) obj;
        return Objects.equals(alias, other.alias) && Objects.equals(group, other.group) && Objects.equals(groupValues, other.groupValues)
                && Objects.equals(id, other.id) && metaType == other.metaType && Objects.equals(name, other.name)
                && Objects.equals(value, other.value);
    }

}
