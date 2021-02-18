package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 
     */
    private static final long serialVersionUID = 2490746870311098179L;

    @ApiModelProperty("Identificador único do layout")
    private Long id;

    @ApiModelProperty("Nome ou título do layout")
    private String name;

    @ApiModelProperty("Descrição do layout")
    private String description;

    @ApiModelProperty("Base64 do layout")
    private String base64;

    @ApiModelProperty("Data da criação do layout")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime createdAt;

    @ApiModelProperty("Lista de parâmetros do layout")
    private List<ReportLayoutParameterDTO> params;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ReportLayoutParameterDTO> getParams() {
        return params;
    }

    public void setParams(List<ReportLayoutParameterDTO> params) {
        this.params = params;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "ReportLayoutDTO [id=" + id + ", name=" + name + ", description=" + description + ", createdAt="
                + createdAt + ", xml=" + base64 + ", params=" + params + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        ReportLayoutDTO other = (ReportLayoutDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
