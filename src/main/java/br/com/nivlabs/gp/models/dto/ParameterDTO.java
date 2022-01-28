package br.com.nivlabs.gp.models.dto;

import java.util.Arrays;

import br.com.nivlabs.gp.enums.MetaType;
import br.com.nivlabs.gp.enums.ParameterAliasType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Parâmetro da apliação")
public class ParameterDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 647659232405015211L;

    @Schema(description = "Identificador único do parâmetro")
    private Long id;

    @Schema(description = "Apelido do parâmetro")
    private ParameterAliasType alias;

    @Schema(description = "Nome do parâmetro")
    private String name;

    @Schema(description = "Grupo do parâmetro")
    private String group;

    @Schema(description = "Metatipo do parâmetro")
    private MetaType metaType;

    @Schema(description = "Valor do parâmetro")
    private String value;

    @Schema(description = "Grupo de valores para metatipos de grupo (GROUP)")
    private String[] groupValues;

    /**
     * Construtor padrão para uso interno
     * 
     * @param id Identificador único do parâmetro
     * @param alias Apelido do parâmetro
     * @param name Nome do parâmetro
     * @param group Grupo do parâmetro
     * @param metaType Metatipo do parâmetro
     * @param value Valor do parâmetro
     * @param groupValues Grupo de valores do parâmetro (Apenas para parâmetros com metatipo de grupo - GROUP)
     */
    public ParameterDTO(Long id, ParameterAliasType alias, String name, String group, MetaType metaType, String value,
            String[] groupValues) {
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

    public ParameterAliasType getAlias() {
        return alias;
    }

    public void setAlias(ParameterAliasType alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
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

    public String[] getGroupValues() {
        return groupValues;
    }

    public void setGroupValues(String[] groupValues) {
        this.groupValues = groupValues;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + Arrays.hashCode(groupValues);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metaType == null) ? 0 : metaType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParameterDTO other = (ParameterDTO) obj;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (!Arrays.equals(groupValues, other.groupValues))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (metaType != other.metaType)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ParameterDTO [id=" + id + ", name=" + name + ", group=" + group + ", metaType=" + metaType + ", value="
                + value + ", groupValues=" + Arrays.toString(groupValues) + "]";
    }

}
