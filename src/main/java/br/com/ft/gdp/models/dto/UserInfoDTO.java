package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe UserInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 5123075975481132373L;

    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private DocumentDTO document;
    private String gender;
    private AddressDTO address;
    private String phoneNumber;
    private String secondaryNumber;
    private Date bornDate;
    private String observations;
}
