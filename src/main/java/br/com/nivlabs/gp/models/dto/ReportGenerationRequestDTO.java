package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe ReportGenerationRequestDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 04 de fevereiro de 2021
 */
@Schema(description = "Requisição de geração de relatório")
public class ReportGenerationRequestDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4069593034981336944L;

    @Schema(description = "Identificador único do layout")
    private Long id;

    @Schema(description = "Nome ou título do layout")
    private String name;

    @Schema(description = "Descrição do layout")
    private String description;

    @Schema(description = "Data da criação do layout")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime createdAt;

    @Schema(description = "Base64 do layout")
    private String base64;

    @Schema(description = "Lista de parâmetros do layout")
    private Set<ReportLayoutParameterDTO> params;

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
     * @return the params
     */
    public Set<ReportLayoutParameterDTO> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Set<ReportLayoutParameterDTO> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportGenerationRequestDTO [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append(", base64=");
        builder.append(base64);
        builder.append(", params=");
        builder.append(params);
        builder.append("]");
        return builder.toString();
    }

}
