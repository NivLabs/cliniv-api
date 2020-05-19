package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe InstituteInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Institute Informations")
public class InstituteInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -3104969606567859458L;

    private String cnpj;

    private String cnes;

    private String name;

    private String corporativeName;

    private String legalNature;

    private String street;

    private String addressNumber;

    private String complement;

    private String postalCode;

    private String state;

    private String neighborhood;

    private String city;

    private String phone;

    private String dependency;

    private String instituteType;

    private String management;

}
