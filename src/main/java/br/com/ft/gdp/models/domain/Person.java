package br.com.ft.gdp.models.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.ft.gdp.models.BaseObject;
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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
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
    private String gender;

    @Column(name = "NOME_COMP_PAI")
    private String fatherName;

    @Column(name = "NOME_COMP_MAE")
    private String motherName;

    @Column(name = "DATA_NASCIMENTO")
    private Date bornDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA")
    private List<PersonAddress> listOfAddress = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private UserApplication user;

}
