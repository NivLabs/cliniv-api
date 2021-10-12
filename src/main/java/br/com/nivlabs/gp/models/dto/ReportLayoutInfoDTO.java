package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;
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
public class ReportLayoutInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4393173881706823820L;

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
     * @return the base64
     */
    public String getBase64() {
        return base64;
    }

    /**
     * @param base64 the base64 to set
     */
    public void setBase64(String base64) {
        this.base64 = base64;
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
     * @return the params
     */
    public List<ReportLayoutParameterDTO> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(List<ReportLayoutParameterDTO> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportLayoutInfoDTO [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", base64=");
        builder.append(base64);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append(", params=");
        builder.append(params);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(base64, createdAt, description, id, name, params);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReportLayoutInfoDTO other = (ReportLayoutInfoDTO) obj;
        return Objects.equals(base64, other.base64) && Objects.equals(createdAt, other.createdAt)
                && Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(params, other.params);
    }

}
