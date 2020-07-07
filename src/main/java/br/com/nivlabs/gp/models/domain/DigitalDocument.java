package br.com.nivlabs.gp.models.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA_EVENTO")
    private AttendanceEvent attendanceEvent;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private DigitalDocumentType type;

    @Column(name = "BASE64")
    @Lob
    private String base64;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime createdAt;

    public DigitalDocumentDTO getDtoFromDomain() {
        DigitalDocumentDTO dto = new DigitalDocumentDTO();
        BeanUtils.copyProperties(this, dto);
        dto.setAttendanceEventId(attendanceEvent.getId());
        return dto;
    }

}
