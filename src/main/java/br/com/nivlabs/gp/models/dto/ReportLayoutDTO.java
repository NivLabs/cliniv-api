package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe ReportLayoutDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@ApiModel(description = "Layout de Relatório")
public class ReportLayoutDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2490746870311098179L;

    @ApiModelProperty("Identificador único do layout")
    private Long id;

    @ApiModelProperty("Nome ou título do layout")
    private String name;

    @ApiModelProperty("Descrição do layout")
    private String description;

    @ApiModelProperty("Data da criação do layout")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime createdAt;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportLayoutDTO [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReportLayoutDTO other = (ReportLayoutDTO) obj;
        return Objects.equals(createdAt, other.createdAt) && Objects.equals(description, other.description) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
    }

}
