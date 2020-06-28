package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.nivlabs.gp.models.domain.EventType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Objeto que representa um evento do atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações do evento do atendimento")
public class AttendanceEventInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4230965183543396561L;

    @ApiModelProperty("Identificador único do evento do atendimento")
    private Long id;
    @ApiModelProperty("Identificador único do paciente do evento do atendimento")
    private Long patientId;
    @ApiModelProperty("Identificador único do profissional responsável pelo evento do atendimento")
    private Long responsibleId;
    @ApiModelProperty("Identificador único do atendimento")
    private Long attendanceId;
    @ApiModelProperty("Documentos gerados pelo evento do atendimento")
    private List<DigitalDocumentDTO> documents;
    @ApiModelProperty("Título do evento do atendimento")
    private String title;
    @ApiModelProperty("Documentos gerados pelo evento do atendimento")
    private String observations;
    private LocalDateTime eventDateTime;
    private EventType type;
}
