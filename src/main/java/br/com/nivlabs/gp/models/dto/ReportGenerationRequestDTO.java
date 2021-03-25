package br.com.nivlabs.gp.models.dto;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe ReportGenerationRequestDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 04 de fevereiro de 2021
 */
@ApiModel("Requisição de geração de relatório")
public class ReportGenerationRequestDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4069593034981336944L;

    @ApiModelProperty("Identificador único do layout")
    private Long id;

    @ApiModelProperty("Nome ou título do layout")
    private String name;

    @ApiModelProperty("Descrição do layout")
    private String description;

    @ApiModelProperty("Base64 do layout")
    private String base64;

    @ApiModelProperty("Lista de parâmetros do layout")
    private Set<ReportLayoutParameterDTO> params;

    public Set<ReportLayoutParameterDTO> getParams() {
        return params;
    }

    public void setParams(Set<ReportLayoutParameterDTO> params) {
        this.params = params;
    }

    public ReportGenerationRequestDTO() {
        super();
    }

    public ReportGenerationRequestDTO(Set<ReportLayoutParameterDTO> params) {
        super();
        this.params = params;
    }

    @Override
    public String toString() {
        return "ReportGenerationRequestDTO [params=" + params + "]";
    }

}
