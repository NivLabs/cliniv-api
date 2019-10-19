package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ft.gdp.models.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PersonAddress.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Entity
@Table(name = "ENDERECO_PF")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PersonAddress extends BaseObject {

    private static final long serialVersionUID = 8579264573046115498L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_PESSOA")
    private Long personId;

    @Column(name = "LOGRADOURO")
    private String street;

    @Column(name = "NUMERO")
    private String addressNumber;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "CODIGO_POSTAL")
    private String postalCode;

    @Column(name = "ESTADO")
    private String state;

    @Column(name = "BAIRRO")
    private String district;

    @Column(name = "CIDADE")
    private String city;
}
