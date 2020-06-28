package br.com.nivlabs.gp.models.dto;

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
@ApiModel("Informações do cliente")
public class CustomerInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6019817310193995713L;

    @ApiModelProperty("Identificador único do Cliente")
    private Long id;

    @ApiModelProperty("CNPJ")
    private String cnpj;

    @ApiModelProperty("Cadastro Nacional de Estabelecimento de saúde")
    private String cnes;

    @ApiModelProperty("Nome da instituição")
    private String name;

    @ApiModelProperty("Telefone")
    private String phone;

    @ApiModelProperty("Nome corporativo")
    private String corporativeName;

    @ApiModelProperty("Natureza Legal")
    private String legalNature;

    @ApiModelProperty("Dependência")
    private String dependency;

    @ApiModelProperty("Tipo da instituição")
    private String instituteType;

    @ApiModelProperty("Gestor responsável")
    private String management;

    @ApiModelProperty("Licença de uso")
    private LicenseDTO license;

    @ApiModelProperty("Endereço da instituição")
    private AddressDTO address;

    @ApiModelProperty("Logotipo da instituição")
    private String logoBase64;
}
