package br.com.nivlabs.gp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe interna para representar os papéis do usuário
 * 
 * @author viniciosarodrigues
 *
 */
@Schema(description = "Papel de acesso (role)")
public class RoleDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -4723369199443894800L;

    @Schema(description = "Identificador único do papel do usuário")
    private Long id;

    @Schema(description = "Nome do papel do usuário")
    private String name;

    @Schema(description = "Descrição do papel do usuáiro")
    private String description;

    public RoleDTO(Long id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        RoleDTO other = (RoleDTO) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RoleDTO [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

}