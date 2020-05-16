package br.com.ft.gdp.models.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Customer Informations")
public class CustomerInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6019817310193995713L;
    private Long id;
    private String cnpj;
    private String cnes;
    private String name;
    private String phone;
    private String corporativeName;
    private String legalNature;
    private String dependency;
    private String instituteType;
    private String management;
    private LicenseDTO license;
    private AddressDTO address;
}
