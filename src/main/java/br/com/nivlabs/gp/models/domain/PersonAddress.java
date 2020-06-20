package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;
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
public class PersonAddress extends BaseObjectWithId {

    private static final long serialVersionUID = 8579264573046115498L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

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
    private String neighborhood;

    @Column(name = "CIDADE")
    private String city;
}
