package br.com.tl.gdp.models.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.tl.gdp.models.BaseObject;
import br.com.tl.gdp.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Person.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Entity
@Table(name = "PESSOA_FISICA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseObject {

    private static final long serialVersionUID = -3719485861961903955L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String firstName;

    @Column(name = "SOBRENOME")
    private String lastName;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "SEXO")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "IDEOLOGIA_GENERO")
    @Enumerated(EnumType.STRING)
    private GenderIdeology genderIdeology;

    @Column(name = "NOME_COMP_PAI")
    private String fatherName;

    @Column(name = "NOME_COMP_MAE")
    private String motherName;

    @Column(name = "DATA_NASCIMENTO")
    private Date bornDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
    private PersonAddress address;

    @Column(name = "TELEFONE_PRINCIPAL")
    private String principalNumber;

    @Column(name = "TELEFONE_SECUNDARIO")
    private String secondaryNumber;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private UserApplication user;

    @Column(name = "FOTO")
    @Lob
    private String profilePhoto;

}
