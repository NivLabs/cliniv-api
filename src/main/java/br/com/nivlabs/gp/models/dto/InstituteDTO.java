package br.com.nivlabs.gp.models.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações da instituição")
public class InstituteDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5796305672599055540L;

    private String appName = "NiV Labs - Gestão de Prontuário";
    private String version = "1.0.0-beta";
    private CustomerInfoDTO customerInfo;
    private Set<ParameterDTO> parameters = new HashSet<>();
}
