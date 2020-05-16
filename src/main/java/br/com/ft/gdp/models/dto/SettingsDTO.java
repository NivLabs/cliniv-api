package br.com.ft.gdp.models.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Setting of Application")
public class SettingsDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5796305672599055540L;

    private final String appName = "FT - Gestão de Prontuário";
    private String version;
    private CustomerInfoDTO customerInfo;
    private Set<ParameterDTO> parameters = new HashSet<>();
}
