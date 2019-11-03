package br.com.ft.gdp.models.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PersonPhoneId.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de nov de 2019
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PersonPhoneId implements Serializable {

    private static final long serialVersionUID = 4804913139653467672L;

    private Long personId;

    private String phoneNumber;
}
