package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("New parameters Request")
public class NewParameterValueDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1842321490525613622L;

    private String newValue;
}
