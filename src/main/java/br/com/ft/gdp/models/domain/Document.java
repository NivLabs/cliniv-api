package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import br.com.ft.gdp.models.BaseObjectWithCreatedAt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe que representa os documentos digitais do prontuário
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Document extends BaseObjectWithCreatedAt {

    private static final long serialVersionUID = 4392117344563755949L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private DigitalDocumentType type;

    @Column(name = "BASE64")
    @Lob
    private String base64;

    @Column(name = "NOME")
    private String name;

}
