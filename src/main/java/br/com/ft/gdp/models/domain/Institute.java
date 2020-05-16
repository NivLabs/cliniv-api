package br.com.ft.gdp.models.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Classe Institute.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Entity
@Table(name = "INSTITUTO")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Institute implements Serializable {

    private static final long serialVersionUID = -8389926388826078313L;

    @Id
    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "CNES")
    private String cnes;

    @Column(name = "NOME")
    private String name;

    @Column(name = "NOME_CORPORATIVO")
    private String corporativeName;

    @Column(name = "NATUREZA_LEGAL")
    private String legalNature;

    @Column(name = "LOGRADOURO")
    private String street;

    @Column(name = "NUMERO")
    private String addressNumber;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "CEP")
    private String postalCode;

    @Column(name = "ESTADO")
    private String state;

    @Column(name = "BAIRRO")
    private String neighborhood;

    @Column(name = "CIDADE")
    private String city;

    @Column(name = "TELEFONE")
    private String phone;

    @Column(name = "DEPENDENCIA")
    private String dependency;

    @Column(name = "TIPO_DE_INSTITUICAO")
    private String instituteType;

    @Column(name = "GESTAO")
    private String management;

    @Column(name = "DATA_DE_REGISTRO")
    private Date licenseDate;

    @Column(name = "USUARIO_DE_REGISTRO")
    private String userOfRegister;

    @Column(name = "CHAVE_ACESSO")
    private String accessKey;

    @Column(name = "DATA_INICIO")
    private Date startDate;

    @Column(name = "DATA_FIM")
    private Date endDate;
}
