package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.DocumentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe DocumentDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Documento")
public class DocumentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8977997168527769344L;

    @ApiModelProperty("Tipo do documento pessoal, ex: CPF, CNPJ, RG")
    @NotNull(message = "O tipo do documeno deve ser informado")
    @NotEmpty(message = "O tipo do documeno deve ser informado")
    @NotBlank(message = "O tipo do documeno deve ser informado")
    private DocumentType type;

    @ApiModelProperty("Valor do documento pessoal")
    @NotNull(message = "O documeno deve ser informado")
    @NotEmpty(message = "O documeno deve ser informado")
    @NotBlank(message = "O documento deve ser informado")
    private String value;
}
