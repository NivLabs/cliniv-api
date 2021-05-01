package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe ReportLayoutParameterDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@ApiModel(description = "Parametros do layout de relatorio")
public class ReportLayoutParameterDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 5486172987281764933L;

    @ApiModelProperty("Identificador único do parâmetro")
    private Long id;

    @ApiModelProperty("Nome do parâmetro")
    private String name;

    @ApiModelProperty("Tipo do parâmetro")
    private String type;

    @ApiModelProperty("Descrição do parâmetro")
    private String description;

    @ApiModelProperty("Valor padrão do parâmetro")
    private String defaultValue;

    // Propriedade utilizada na geração do relatório
    @ApiModelProperty("Valor informado do parâmetro")
    private String value;

    public ReportLayoutParameterDTO() {
        super();
    }

    public ReportLayoutParameterDTO(Long id, String name, String type, String description, String defaultValue, String value) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.defaultValue = defaultValue;
        this.value = value;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReportLayoutParameterDTO [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description
                + ", defaultValue=" + defaultValue + ", value=" + value + "]";
    }
}
