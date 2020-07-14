package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.PrescriptionItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@ApiModel("Item da prescrição do paciente")
public class PrescriptionItemDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2268618756493323347L;

    @ApiModelProperty("Identificador único do item da prescrição do paciente")
    @NotNull(message = "Informe o identificador sequencial do item da prescrição")
    private Long id;

    @ApiModelProperty("Componente do item da prescrição")
    @NotEmpty(message = "Informe qual é o componente que o item representa (Descrição do Medicamento ou procedimento)")
    private String component;

    @ApiModelProperty("Quantidade de uso (Se houver)")
    private Double quantity;

    @ApiModelProperty("Unidade de medida (Se houver")
    private String measureUnit;

    @ApiModelProperty("Tipo do item (MEDICINE OU PROCEDURE")
    @NotNull(message = "Informe o tipo do item da prescrição")
    private PrescriptionItemType type;

    @ApiModelProperty("Instruções do item da prescrição (Se houver)")
    private String instructions;

}
