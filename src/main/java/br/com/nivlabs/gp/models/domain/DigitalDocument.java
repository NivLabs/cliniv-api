package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import br.com.nivlabs.gp.models.BaseObjectWithCreatedAt;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
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
@Entity
@Table(name = "DOCUMENTO_DIGITAL")
public class DigitalDocument extends BaseObjectWithCreatedAt {

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

    public DigitalDocumentDTO getDtoFromDomain() {
        DigitalDocumentDTO dto = new DigitalDocumentDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }

}
