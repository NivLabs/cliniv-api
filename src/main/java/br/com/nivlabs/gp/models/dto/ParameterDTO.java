package br.com.nivlabs.gp.models.dto;

import java.util.Arrays;

import br.com.nivlabs.gp.enums.MetaType;
import io.swagger.annotations.ApiModel;

/**
 * Parâmetros da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "Parâmetro da apliação")
public class ParameterDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 647659232405015211L;

    private Long id;

    private String name;

    private String group;

    private MetaType metaType;

    private String value;

    private String[] groupValues;

	public ParameterDTO(Long id, String name, String group, MetaType metaType, String value, String[] groupValues) {
		super();
		this.id = id;
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
