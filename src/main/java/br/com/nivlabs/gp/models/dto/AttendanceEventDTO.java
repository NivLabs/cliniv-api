package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitEventDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de nov de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Evento do Atendimento")
public class AttendanceEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -5192545539633937184L;

    @ApiModelProperty("Identificador único do evento do atendimento")
    private Long id;

    @ApiModelProperty("Data e hora do evento do atendimento")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

    @ApiModelProperty("Descrição do evento do atendimento")
    private String description;

    @ApiModelProperty("Documentos gerados pelo evento do atendimento")
    private List<DigitalDocumentDTO> documents;
}
