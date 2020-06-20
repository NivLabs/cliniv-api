package br.com.nivlabs.gp.models.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotEmpty
@EqualsAndHashCode(callSuper = true)
@ApiModel("Alergias do paciente")
public class PatientAllergiesDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 7849783306168301683L;

    @ApiModelProperty("Nomes das alergias")
    private List<String> descriptions;

}
