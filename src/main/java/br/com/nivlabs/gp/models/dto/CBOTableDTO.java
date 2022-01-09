package br.com.nivlabs.gp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tabela CBO")
public class CBOTableDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 480715163245338513L;

    @Schema(description = "Identificador único")
    private Long id;
    @Schema(description = "Descrição")
    private String description;

    public CBOTableDTO() {
        super();
    }

    public CBOTableDTO(Long id, String description) {
        super();
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        CBOTableDTO other = (CBOTableDTO) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "CBOTableDTO [id=" + id + ", description=" + description + "]";
    }

}
