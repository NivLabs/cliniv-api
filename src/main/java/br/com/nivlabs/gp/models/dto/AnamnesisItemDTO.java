package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.models.enums.MetaType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Item da Anamnese")
public class AnamnesisItemDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -1666755500493520346L;

    @NotNull
    @ApiModelProperty("Identificador único do item da anamnese")
    private Long id;

    @ApiModelProperty("Questão do item da anamnese")
    @NotNull(message = "Informe a questão")
    private String question;

    @ApiModelProperty("Tipo da questão")
    @NotNull(message = "Informe o tipo da questão")
    private MetaType metaType;

}
